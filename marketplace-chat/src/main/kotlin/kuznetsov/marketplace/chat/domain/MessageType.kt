package kuznetsov.marketplace.chat.domain

enum class MessageType {

    LOGIN_ACCEPTED,
    LOGIN_DECLINED,
    LOGIN_REQUIRED,

    JOIN,
    LEFT,

    CHAT,

    COMMAND_RESULT,
    COMMAND_ERROR,

}
