CREATE TABLE `category` (
                            `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                            `parent_id` int(11) unsigned DEFAULT NULL,
                            `name` varchar(32) DEFAULT NULL,
                            `type_flag` char(1) DEFAULT 'N' COMMENT '일반:N , ',
                            `sort` tinyint(4) NOT NULL DEFAULT '10' COMMENT '순서',
                            `is_active` tinyint(1) NOT NULL DEFAULT '0' COMMENT '사용 여부',
                            `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                            `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                            `delete_date` timestamp NULL DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='카테고리';

CREATE TABLE `category_has_tag` (
                                    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                    `category_id` int(11) unsigned NOT NULL,
                                    `tag_id` int(11) unsigned NOT NULL,
                                    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`),
                                    KEY `idx_category_has_tag_category_id` (`category_id`),
                                    KEY `idx_category_has_tag_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카테고리 태그 테이블 조인';


CREATE TABLE `device` (
                          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                          `device_key` varchar(64) NOT NULL COMMENT '사용자 디바이스 키',
                          `version` varchar(10) DEFAULT NULL COMMENT '사용중인 어플리케이션 버전',
                          `device` varchar(30) DEFAULT NULL COMMENT '사용중인 모델',
                          `platform` varchar(10) DEFAULT NULL COMMENT '사용중인 사용자 OS',
                          `platform_version` varchar(10) DEFAULT NULL COMMENT '사용중인 OS 버전',
                          `adid` varchar(100) DEFAULT NULL COMMENT '광고식별자, adid/idfa',
                          `is_rooted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '루팅여부',
                          `language` varchar(2) DEFAULT NULL COMMENT '언어코드',
                          `ip` varchar(50) DEFAULT NULL COMMENT '접속아이피',
                          `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                          `delete_date` timestamp NULL DEFAULT NULL COMMENT '삭제일',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `device_key` (`device_key`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COMMENT='사용자 디바이스 테이블 ';


CREATE TABLE `device_model` (
                                `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                `platform` char(1) NOT NULL,
                                `brand` varchar(100) DEFAULT NULL,
                                `name` varchar(200) DEFAULT NULL,
                                `device` varchar(100) DEFAULT NULL,
                                `model` varchar(100) NOT NULL,
                                `create_date` timestamp NOT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=325887 DEFAULT CHARSET=utf8;


CREATE TABLE `server_error_log` (
                                    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                    `user_id` int(11) DEFAULT NULL,
                                    `server` char(1) NOT NULL DEFAULT '',
                                    `os` char(1) DEFAULT NULL,
                                    `os_version` varchar(10) DEFAULT NULL,
                                    `url` varchar(255) NOT NULL,
                                    `version` varchar(10) DEFAULT NULL,
                                    `method` varchar(10) DEFAULT NULL,
                                    `request` text,
                                    `message` text NOT NULL,
                                    `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;


CREATE TABLE `server_status` (
                                 `id` int(11) unsigned DEFAULT NULL,
                                 `server` char(1) DEFAULT NULL,
                                 `message` varchar(100) DEFAULT NULL,
                                 `is_active` tinyint(1) NOT NULL,
                                 `update_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tag` (
                       `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                       `name` varchar(32) NOT NULL,
                       `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카테고리';



CREATE TABLE `term` (
                        `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                        `name` varchar(100) DEFAULT NULL,
                        `group_id` int(10) unsigned NOT NULL,
                        `type_flag` char(1) DEFAULT NULL COMMENT '약관 타입 U:이용약관, P:개인정보, E:기타',
                        `url` varchar(256) DEFAULT NULL COMMENT '웹뷰 URL',
                        `is_essential` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '필수/선택 여부(1: 필수, 0: 선택)',
                        `is_active` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '게시 여부(1: 활성, 0: 비활성)',
                        `sort` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '조회 순서',
                        `create_id` int(10) unsigned DEFAULT NULL COMMENT '생성자 id',
                        `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        `term_date` timestamp NULL DEFAULT NULL,
                        `update_id` int(10) unsigned DEFAULT NULL COMMENT '수정자 id',
                        `update_date` timestamp NULL DEFAULT NULL COMMENT '수정일',
                        `delete_id` int(10) unsigned DEFAULT NULL COMMENT '삭제자 id',
                        `delete_date` timestamp NULL DEFAULT NULL COMMENT '삭제일',
                        PRIMARY KEY (`id`),
                        KEY `term_term_group_id` (`group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='이용약관';





CREATE TABLE `term_group` (
                              `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                              `name` varchar(50) NOT NULL COMMENT '이용약관 그룹명',
                              `description` varchar(300) NOT NULL COMMENT '설명',
                              `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              `create_id` int(11) DEFAULT NULL,
                              `update_date` timestamp NULL DEFAULT NULL,
                              `update_id` int(11) DEFAULT NULL,
                              `delete_date` timestamp NULL DEFAULT NULL,
                              `delete_id` int(11) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='이용약관그룹';



CREATE TABLE `user` (
                        `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                        `uuid` varchar(36) NOT NULL COMMENT 'UUID',
                        `nick_name` varchar(50) NOT NULL COMMENT '이름',
                        `thumbnail_path` varchar(128) DEFAULT NULL COMMENT 'host 제외한 썸네일 URL',
                        `status_flag` char(1) NOT NULL DEFAULT 'A' COMMENT '가입상태 (A:활성, D:탈퇴, P:중지)',
                        `is_alert_push` tinyint(1) DEFAULT '1' COMMENT '푸시 알림 여부',
                        `is_alert_promotion` tinyint(1) DEFAULT '1' COMMENT '프로모션/이벤트 알림 여부',
                        `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                        `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                        `create_id` int(10) unsigned DEFAULT NULL COMMENT '생성자 id',
                        `update_id` int(10) unsigned DEFAULT NULL,
                        `delete_date` timestamp NULL DEFAULT NULL COMMENT '탈퇴 일자',
                        `delete_id` int(10) unsigned DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uuid` (`uuid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='사용자 테이블';




CREATE TABLE `user_access_log` (
                                   `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                   `user_id` int(10) unsigned NOT NULL,
                                   `device_id` int(10) unsigned NOT NULL,
                                   `user_agent` varchar(255) DEFAULT NULL,
                                   `ip` varchar(50) DEFAULT NULL COMMENT '접속아이피',
                                   `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   KEY `idx_user_access_log_user_id` (`user_id`),
                                   KEY `idx_user_access_log_device_id` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='로그인 로그';



CREATE TABLE `user_account` (
                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                `user_id` int(10) unsigned NOT NULL,
                                `bank_name` varchar(32) DEFAULT NULL COMMENT '은행명',
                                `bank_holder_name` varchar(30) DEFAULT NULL COMMENT '예금주명',
                                `bank_account_number` varchar(64) DEFAULT NULL COMMENT 'sns unique id',
                                `tel` varchar(20) DEFAULT NULL,
                                `name` varchar(36) DEFAULT NULL COMMENT '이름',
                                `email` varchar(50) DEFAULT NULL,
                                `weight` tinyint(255) DEFAULT NULL,
                                `height` tinyint(255) DEFAULT NULL,
                                `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                                `update_date` timestamp NULL DEFAULT NULL COMMENT '업데이트 일자',
                                PRIMARY KEY (`id`),
                                KEY `idx_user_account_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 계정 정보';




CREATE TABLE `user_address` (
                                `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                `user_id` int(10) unsigned NOT NULL COMMENT '유저 아이디',
                                `type_flag` char(1) DEFAULT NULL COMMENT '구분: HOME, OFFICE, FREQUENT',
                                `zip_code` varchar(10) DEFAULT NULL COMMENT '우편 번호',
                                `road_address` varchar(256) DEFAULT NULL COMMENT '도로명 주소',
                                `jibun_address` varchar(256) DEFAULT NULL COMMENT '지번 주소',
                                `address_extra` varchar(128) DEFAULT NULL COMMENT '상세. 주소',
                                `lat` varchar(20) DEFAULT NULL COMMENT '위도',
                                `lng` varchar(20) DEFAULT NULL COMMENT '경도',
                                `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                                `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                                PRIMARY KEY (`id`),
                                KEY `idx_user_address_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 주소 정보';





CREATE TABLE `user_client_detail` (
                                      `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                      `client_id` varchar(256) NOT NULL,
                                      `client_secret` varchar(256) NOT NULL,
                                      `scope` varchar(300) NOT NULL,
                                      `access_token_validity` bigint(20) DEFAULT NULL,
                                      `refresh_token_validity` bigint(20) DEFAULT NULL,
                                      `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                                      `update_date` timestamp NULL DEFAULT NULL COMMENT '수정일',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


CREATE TABLE `user_has_category` (
                                     `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                     `user_id` int(11) unsigned NOT NULL,
                                     `category_id` int(11) unsigned NOT NULL,
                                     `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                     PRIMARY KEY (`id`),
                                     KEY `idx_user_has_category_user_id` (`user_id`),
                                     KEY `idx_user_has_category_category_id` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='유저 추천 카테고리';


CREATE TABLE `user_has_device` (
                                   `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                   `user_id` int(10) unsigned NOT NULL,
                                   `device_id` int(10) unsigned NOT NULL,
                                   `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   PRIMARY KEY (`id`),
                                   KEY `idx_user_has_device_partner_id` (`user_id`),
                                   KEY `idx_user_has_device_device_id` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='회원 디바이스 테이블 조인';


CREATE TABLE `user_has_term` (
                                 `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
                                 `user_id` int(11) unsigned NOT NULL COMMENT '유저 아이디',
                                 `term_id` int(11) unsigned NOT NULL COMMENT '약관 id',
                                 `is_agree` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '동의 여부',
                                 `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_user_has_term_user_id` (`user_id`),
                                 KEY `idx_user_has_term_term_id` (`term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='사용자 이용약관 로그 테이블';



CREATE TABLE `user_push` (
                             `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                             `user_id` int(10) unsigned NOT NULL,
                             `platform_flag` char(1) NOT NULL COMMENT 'I:IOS,A:Android',
                             `push_key` varchar(256) NOT NULL COMMENT '푸쉬키',
                             `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                             `last_date` timestamp NULL DEFAULT NULL COMMENT '마지막 업데이트 일자',
                             PRIMARY KEY (`id`),
                             KEY `idx_user_push_user_id` (`user_id`),
                             KEY `push_key` (`push_key`),
                             KEY `platform_flag` (`platform_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='사용자 푸쉬키 테이블';


CREATE TABLE `user_sns` (
                            `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                            `user_id` int(10) unsigned NOT NULL,
                            `unique_id` varchar(128) DEFAULT NULL COMMENT 'sns unique id',
                            `social_flag` char(1) NOT NULL COMMENT ' K:카카오, N:네이버, A:애플',
                            `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                            `update_date` timestamp NULL DEFAULT NULL COMMENT '업데이트 일자',
                            PRIMARY KEY (`id`),
                            KEY `idx_user_sns_user_id` (`user_id`),
                            KEY `idx_user_sns_unique_id` (`unique_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='사용자 SNS';


CREATE TABLE `user_token` (
                              `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                              `user_id` int(10) unsigned NOT NULL,
                              `client_id` int(10) unsigned NOT NULL,
                              `access_token` varchar(500) NOT NULL,
                              `refresh_token` varchar(36) NOT NULL,
                              `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
                              `update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
                              `expire_date` timestamp NOT NULL COMMENT '리프레쉬 토큰 만료일',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `access_token` (`access_token`),
                              UNIQUE KEY `refresh_token` (`refresh_token`),
                              KEY `idx_user_token_user_id` (`user_id`),
                              KEY `idx_user_token_access_id` (`access_id`),
                              KEY `idx_user_token_client_id` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='사용자 엑세스 토큰';


CREATE TABLE `version` (
                           `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                           `platform_flag` char(1) NOT NULL COMMENT 'I: 아이폰, A:안드로이드',
                           `min_version` varchar(30) NOT NULL COMMENT '최소 버전',
                           `max_version` varchar(30) NOT NULL COMMENT '최대 버전',
                           `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일자',
                           `update_date` timestamp NULL DEFAULT NULL COMMENT '업데이트 일자',
                           `type_flag` char(1) NOT NULL,
                           `min_trans` int(11) unsigned NOT NULL,
                           `max_trans` int(11) unsigned NOT NULL,
                           `market_url` varchar(256) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='버전';