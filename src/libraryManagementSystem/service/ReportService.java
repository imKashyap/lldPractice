package libraryManagementSystem.service;

public interface ReportService {
    String generateAvailabilityReport();

    String generateOverdueReport();

    String generatePopularBooksReport();
}
