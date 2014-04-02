CREATE  TABLE IF NOT EXISTS `HB`.`Blogger` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `create_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ,
  `update_date` TIMESTAMP NULL ,
  `status` VARCHAR(45) NULL ,
  `name` VARCHAR(255) NULL ,
  `description` VARCHAR(255) NULL ,
  `priority` INT NULL ,
  `type` VARCHAR(45) NULL ,
  `size` INT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB;