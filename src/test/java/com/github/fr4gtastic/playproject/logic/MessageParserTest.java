package com.github.fr4gtastic.playproject.logic;

import org.junit.jupiter.api.Test;

import static com.github.fr4gtastic.playproject.dto.EvaluationOption.STOP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageParserTest {

    private MessageParser messageParser = new MessageParser();

    @Test
    void isEvaluationOptionTest() {
        var message = "START";
        assertTrue(messageParser.isEvaluationOption(message));
    }

    @Test
    void isEvaluationOptionFalseTest() {
        var message = "STOPP";
        assertFalse(messageParser.isEvaluationOption(message));
    }

    @Test
    void toEvaluationOptionTest() {
        assertEquals(STOP, messageParser.toEvaluationOption("STOP"));
    }

    @Test
    void toEvaluationOptionWrongInputTest() {
        assertThrows(IllegalArgumentException.class, () -> messageParser.toEvaluationOption("test1"));
    }

    @Test
    void getUriFromMessageTest() {
        var message = "www.test.pl";
        assertEquals("www.test.pl", messageParser.getUriFromMessage(message));
    }

    @Test
    void getUriFromMessageLongerTest() {
        var message = "Visit www.test.pl";
        assertEquals("www.test.pl", messageParser.getUriFromMessage(message));
    }

    @Test
    void getUriFromMessageEmptyResultTest() {
        var message = "Visit my page";
        assertEquals("", messageParser.getUriFromMessage(message));
    }
}
