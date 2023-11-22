CREATE SCHEMA `bulletin` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `bulletin`.`post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `description` VARCHAR(500) NULL,
  `status` INT NULL,
  `created_user_id` INT NULL,
  `updated_user_id` INT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;