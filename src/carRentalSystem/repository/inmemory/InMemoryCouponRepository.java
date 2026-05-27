package carRentalSystem.repository.inmemory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import carRentalSystem.models.Coupon;
import carRentalSystem.repository.CouponRepository;

public class InMemoryCouponRepository implements CouponRepository {
    private final Map<String, Coupon> couponsByCode = new ConcurrentHashMap<>();

    @Override
    public void save(Coupon coupon) {
        couponsByCode.put(coupon.getCouponCode(), coupon);
    }

    @Override
    public Optional<Coupon> findByCode(String couponCode) {
        return Optional.ofNullable(couponsByCode.get(couponCode));
    }
}
