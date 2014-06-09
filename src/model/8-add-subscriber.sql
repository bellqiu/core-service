CREATE  TABLE IF NOT EXISTS `HB`.`Subscriber` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `create_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NULL ,
  `status` VARCHAR(45) NULL ,
  `email` VARCHAR(200) NULL ,
  `priority` INT NULL ,
  `send_count` INT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;