package com.devopsbuddy.web.i18n;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.logging.Logger;

@Service
public class I18nService {

    /** The application logger */
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(I18nService.class);

    @Autowired
    private MessageSource messageSource;

    /**
     * Returns a message for the given message id and the default locale in the session context
     * @param messageId The key to the messages recourse file
     **/
    public String getMessage(String messageId){
        LOG.info("Returnign i18n text for messageId {}", messageId);
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(messageId,locale);
    }

    /**
     * Returns a message for the given message id and locale
     * @param messageId The key to the messages resource file
     * @param locale The locale
     * @return Returns a message for the given message id and locale
     */
    public String getMessage(String messageId, Locale locale){
        return messageSource.getMessage(messageId,null,locale);
    }
}
