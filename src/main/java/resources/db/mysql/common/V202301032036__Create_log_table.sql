create table log.logging_event
(
    timestmp          bigint       not null,
    formatted_message text         not null,
    logger_name       varchar(254) not null,
    level_string      varchar(254) not null,
    thread_name       varchar(254) null,
    reference_flag    smallint     null,
    arg0              varchar(254) null,
    arg1              varchar(254) null,
    arg2              varchar(254) null,
    arg3              varchar(254) null,
    caller_filename   varchar(254) not null,
    caller_class      varchar(254) not null,
    caller_method     varchar(254) not null,
    caller_line       char(4)      not null,
    event_id          bigint auto_increment
        primary key
);

CREATE TABLE log.logging_event_exception
(
    `event_id`   bigint(20)   NOT NULL,
    `i`          smallint(6)  NOT NULL,
    `trace_line` varchar(254) NOT NULL,
    PRIMARY KEY (`event_id`, `i`),
    CONSTRAINT `logging_event_exception_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `logging_event` (`event_id`)
);

create table log.logging_event_property
(
    event_id     bigint       not null,
    mapped_key   varchar(254) not null,
    mapped_value text         null,
    primary key (event_id, mapped_key),
    constraint logging_event_property_ibfk_1
        foreign key (event_id) references log.logging_event (event_id)
);

create table log.query_logging_event
(
    timestmp          bigint       not null,
    formatted_message text         not null,
    logger_name       varchar(254) not null,
    level_string      varchar(254) not null,
    thread_name       varchar(254) null,
    reference_flag    smallint     null,
    arg0              varchar(254) null,
    arg1              varchar(254) null,
    arg2              varchar(254) null,
    arg3              varchar(254) null,
    caller_filename   varchar(254) not null,
    caller_class      varchar(254) not null,
    caller_method     varchar(254) not null,
    caller_line       char(4)      not null,
    event_id          bigint auto_increment
        primary key
);

