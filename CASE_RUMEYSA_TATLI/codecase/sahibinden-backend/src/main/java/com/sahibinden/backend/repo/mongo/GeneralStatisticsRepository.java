package com.sahibinden.backend.repo.mongo;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralStatisticsRepository {
    void updateStatisticsForImpression(String adId);
    void updateStatisticsForClick(String adId);
    void updateStatisticsForImpressionWithVisitor(String adId, String visitorId);
}
