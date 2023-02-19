package company.tap.java.assessment.utils.client.rest;

import company.tap.java.assessment.dto.MerchantDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@NoArgsConstructor
public class MerchantWebhookClient {
    @Autowired
    private RestTemplate restTemplate;

    @Async
    public void sendWebhook(MerchantDto merchantDto) {
        String webhookUrl = merchantDto.getWebhookUrl();
        log.info("Initiating Webhook post on this url: {}", webhookUrl);
        try {
            restTemplate.postForObject(webhookUrl, merchantDto, MerchantDto.class);
        } catch (IllegalArgumentException exception) {
            log.info("Unable to reach webhook: {}", exception.getMessage());
        }
    }
}
