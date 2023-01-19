desc user;

insert into user values(no,'hym','gjdbal111@naver.com',password('1234'),'female',now());

select * from user;

select no, name from user where email='gjdbal111@naver.com' and password = password('1234');