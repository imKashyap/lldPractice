package libraryManagementSystem;

import libraryManagementSystem.service.CatalogService;
import libraryManagementSystem.service.CirculationService;
import libraryManagementSystem.service.MembershipService;
import libraryManagementSystem.service.NotificationService;
import libraryManagementSystem.service.ReportService;

public class Library {
    private final CatalogService catalogService;
    private final MembershipService membershipService;
    private final CirculationService circulationService;
    private final NotificationService notificationService;
    private final ReportService reportService;

    public Library(
            CatalogService catalogService,
            MembershipService membershipService,
            CirculationService circulationService,
            NotificationService notificationService,
            ReportService reportService) {
        this.catalogService = catalogService;
        this.membershipService = membershipService;
        this.circulationService = circulationService;
        this.notificationService = notificationService;
        this.reportService = reportService;
    }

    public CatalogService getCatalogService() {
        return catalogService;
    }

    public MembershipService getMembershipService() {
        return membershipService;
    }

    public CirculationService getCirculationService() {
        return circulationService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public ReportService getReportService() {
        return reportService;
    }
}
