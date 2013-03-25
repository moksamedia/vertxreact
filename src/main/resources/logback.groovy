import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

appender('iamconsole', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%-5p %c %x - %m%n'
    }
}