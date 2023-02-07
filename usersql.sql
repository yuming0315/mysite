desc user;

insert into user values(no,'admin','admin',password('admin'),'female',"ADMIN",now());

select * from user;


select no, name from user where email='gjdbal111@naver.com' and password = password('1234');

select no,name,email,gender from user where no = ?;

delete from user where name = 0;


select * from guestbook;

alter table user add column role enum("ADMIN","USER") default "USER" after gender;

desc board;

select * from board;

select max(g_no) from board as a;

insert into board select null,3,'11','1234',0,now(),max(a.g_no)+1,0,0 from board as a;

insert into board values(null,3,'1234','1234',0,sysdate(),(select max(a.g_no)+1 from board as a),0,0);

insert into board value(null,3,'1234','1234',0,sysdate(),(select max(a.g_no)+1 from board as a),0,0);

select null,3,'1','1234',0,sysdate(),max(a.g_no)+1,0,0 
from board as a;

update board set title='yum' where no=1;

delete from board where no="11";

desc site;

insert into site values(null,'MySite','안녕하세요. 허유미의 mysite에 오신 것을 환영합니다.
','/assets/images/profile.jpg','이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.\n
메뉴는 사이트 소개, 방명록, 게시판이 있구요.\n Java 수업 + 데이터베이스 수업 + 웹 프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트 입니다.');

select * from site;
update site set title='YourSite' where no=1;

select title,welcome,profile,description
			from site
			order by no
			limit 0,1;

SET GLOBAL time_zone='+09:00';
SET time_zone='+09:00';

select now();

select @@global.time_zone, @@session.time_zone,@@system_time_zone;

SELECT sysdate(), @@system_time_zone AS TimeZone;