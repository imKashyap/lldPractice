package movieTicketBookingSystem.service;

import java.time.LocalDate;
import java.util.List;

import movieTicketBookingSystem.model.Coupon;
import movieTicketBookingSystem.model.Discount;
import movieTicketBookingSystem.model.DiscountType;
import movieTicketBookingSystem.model.PriceBreakup;
import movieTicketBookingSystem.model.ShowSeat;
import movieTicketBookingSystem.repository.CouponRepository;
import movieTicketBookingSystem.repository.ShowSeatRepository;

public class PricingService {
    private final CouponRepository couponRepository;
    private final ShowSeatRepository showSeatRepository;

    public PricingService(CouponRepository couponRepository, ShowSeatRepository showSeatRepository) {
        this.couponRepository = couponRepository;
        this.showSeatRepository = showSeatRepository;
    }

    public PriceBreakup calculateAmount(String showId, List<String> seatIds, String couponCode) {
        List<ShowSeat> showSeats = showSeatRepository.findByShowIdAndSeatIds(showId, seatIds);
        if (showSeats.size() != seatIds.size()) {
            throw new IllegalArgumentException("One or more seats do not exist for show");
        }
        double grossAmount = showSeats.stream().mapToDouble(ShowSeat::getPrice).sum();
        return new PriceBreakup(grossAmount, applyCoupon(grossAmount, couponCode));
    }

    public Discount applyCoupon(double amount, String couponCode) {
        if (couponCode == null || couponCode.isBlank()) {
            return new Discount(null, 0);
        }

        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid coupon code"));
        if (!coupon.isValidOn(LocalDate.now())) {
            throw new IllegalStateException("Coupon is not active or not valid today");
        }

        double discountAmount = switch (coupon.getDiscountType()) {
            case FLAT -> coupon.getDiscountValue();
            case PERCENTAGE -> amount * coupon.getDiscountValue() / 100;
        };
        if (coupon.getDiscountType() == DiscountType.PERCENTAGE) {
            discountAmount = Math.min(discountAmount, coupon.getMaxDiscount());
        }
        return new Discount(couponCode, Math.min(amount, discountAmount));
    }
}
