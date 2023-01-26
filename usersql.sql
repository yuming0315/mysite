desc user;

insert into user values(no,'hym','gjdbal111@naver.com',password('1234'),'female',now());

select * from user;

select no, name from user where email='gjdbal111@naver.com' and password = password('1234');

select no,name,email,gender from user where no = ?;

delete from user where name = 0;

desc board;

select * from board;

select max(g_no) from board as a;

insert into board select null,3,'11','1234',0,now(),max(a.g_no)+1,0,0 from board as a;

insert into board values(null,3,'1234','1234',0,sysdate(),(select max(a.g_no)+1 from board as a),0,0);

insert into board value(null,3,'1234','1234',0,sysdate(),(select max(a.g_no)+1 from board as a),0,0);

select null,3,'1','1234',0,sysdate(),max(a.g_no)+1,0,0 
from board as a;

SET GLOBAL time_zone='+09:00';
SET time_zone='+09:00';

select now();

select @@global.time_zone, @@session.time_zone,@@system_time_zone;

SELECT sysdate(), @@system_time_zone AS TimeZone;