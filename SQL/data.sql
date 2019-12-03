//Creating movie List table
CREATE  TABLE `moviecruiser`.`movie_list` (
  `ml_id` INT NOT NULL ,
  `ml_title` VARCHAR(45) NULL ,
  `ml_box_office` VARCHAR(45) NULL ,
  `ml_active` VARCHAR(3) NULL ,
  `ml_date_of_launch` DATE NULL ,
  `ml_genre` VARCHAR(45) NULL ,
  `ml_has_teaser` VARCHAR(3) NULL ,
  PRIMARY KEY (`ml_id`) );

//Creating user table
CREATE  TABLE `moviecruiser`.`user` (
  `us_id` INT NOT NULL ,
  `us_name` VARCHAR(45) NULL ,
  PRIMARY KEY (`us_id`) );

//Creating table favorite
CREATE TABLE favorite (
    ft_id int NOT NULL,
    ft_us_id int,
    ft_pr_id int,
    PRIMARY KEY (ft_id),
    FOREIGN KEY (ft_us_id) REFERENCES user(us_id),
    FOREIGN KEY (ft_pr_id) REFERENCES movie_list(ml_id)
    
);

//INSERTING VALUES INTO MOVIELIST TABLE  Admin
INSERT INTO `moviecruiser`.`movie_list` (`ml_id`, `ml_title`, `ml_box_office`, `ml_active`, `ml_date_of_launch`, `ml_genre`, `ml_has_teaser`) VALUES (1, 'Avatar', '2,787,965,087', 'Yes', '2017-03-15', 'Science Fiction', 'Yes');
INSERT INTO `moviecruiser`.`movie_list` (`ml_id`, `ml_title`, `ml_box_office`, `ml_active`, `ml_date_of_launch`, `ml_genre`, `ml_has_teaser`) VALUES (2, 'The Avengers', '1,518,812,988', 'Yes', '2017-12-23', 'Superhero', 'No');
INSERT INTO `moviecruiser`.`movie_list` (`ml_id`, `ml_title`, `ml_box_office`, `ml_active`, `ml_date_of_launch`, `ml_genre`, `ml_has_teaser`) VALUES (3, 'Titanic', '2,187,463,944', 'Yes', '2017-08-21', 'Romance', 'No');
INSERT INTO `moviecruiser`.`movie_list` (`ml_id`, `ml_title`, `ml_box_office`, `ml_active`, `ml_date_of_launch`, `ml_genre`, `ml_has_teaser`) VALUES (4, 'Jurassic World', '1,671,713,208', 'No', '2017-07-02', 'Science Fiction', 'Yes');
INSERT INTO `moviecruiser`.`movie_list` (`ml_id`, `ml_title`, `ml_box_office`, `ml_active`, `ml_date_of_launch`, `ml_genre`, `ml_has_teaser`) VALUES (5, 'Avengers:End Game', '2,750,760,348', 'Yes', '2022-11-02', 'Superhero', 'Yes');

//inserting values into user table
INSERT INTO `moviecruiser`.`user` (`us_id`, `us_name`) VALUES (111111, 'saranya');
INSERT INTO `moviecruiser`.`user` (`us_id`, `us_name`) VALUES (760862, 'saran');

//View movie List Customer 
select *from movie_list where ml_active='Yes' AND ml_date_of_launch<=now();

//Edit Movie List
select *from movie_list where ml_id=3;
update movie_list set ml_title='Frozen 2', ml_box_office='2,656,867,65', ml_active='Yes', ml_date_of_launch='2018-09-10', ml_genre='Comedy', ml_has_teaser='No' where ml_id=1;

//Add to Favorites
insert into favorite(ft_id,ft_us_id,ft_pr_id) values(1,111111,1),(2,760862,2);

//View Favorites
select * from movie_list m inner join favorite f on m.ml_id=f.ft_id and f.ft_id=1;
           //Total no of favorites
select count(ft_id) as tot_no_fav from movie_list m inner join favorite f on m.ml_id=f.ft_id;

//Remove from Favorites
delete from favorite where ft_us_id=1 and ft_pr_id=1;