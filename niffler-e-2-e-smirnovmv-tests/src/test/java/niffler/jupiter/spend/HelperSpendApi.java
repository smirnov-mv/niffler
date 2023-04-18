package niffler.jupiter.spend;

import niffler.api.NifflerSpendClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class HelperSpendApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelperSpendApi.class);

    private static void findAndDeleteSpendsForUserName(String userName) {
        List<String> spends = new ArrayList<>();
        new NifflerSpendClient().getUserSpends(userName)
                .stream()
                .filter(spendJson -> spendJson.id() != null)
                .forEach(spendJson -> spends.add(spendJson.id()));
        if (spends.size() > 0) {
            LOGGER.info("Delete [{}] spends [{}]", userName, spends);
            new NifflerSpendClient().deleteSpends(userName, spends);
        } else LOGGER.info("Spends for user [{}] not found", userName);

    }

    static void deleteSingleSpendForUser(String userName, String spendId) {
        LOGGER.info("Delete spend [{}] for user [{}]", spendId, userName);
        new NifflerSpendClient().deleteSpends(userName, List.of(spendId));
    }

    static void deleteSpends(Set<String> userNames) {
        LOGGER.info("Delete all spends for users [{}]", userNames);
        userNames.forEach(HelperSpendApi::findAndDeleteSpendsForUserName);
    }
}
