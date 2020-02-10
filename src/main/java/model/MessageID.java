package model;

    public class MessageID {
        private static long messageId = -1;
        public static long getMessageID() {
            return ++messageId;
        }
    }
