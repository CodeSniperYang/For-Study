package pack_;

/*
  Y 管理员信息和医护人员信息均存放于数据库MedicalFacilities中
  //创建数据库
  create database MedicalFacilities;
  
     管理员表 Administrators
  mysql> create table Administrators(
    -> adminID varchar(20) not null,
    -> adminPW varchar(20) not null
    -> );
    
    mysql> insert into Administrators values
    -> ('root','root'),
    -> ('yy','yy');
    
     医护人员表 Medicalstaff
  mysql> create table Medicalstaff(
    -> meID varchar(20) not null,
    -> mePW varchar(20) not null
    -> );
    mysql> insert into Medicalstaff values
    -> ('yh1','yh1'),
    -> ('yh2','yh2');
    
 */

/*
mysql> create table equipment (
    -> eid int not null ,
    -> ename varchar(20)
    -> );
    
+-----+-----------+
| eid | ename     |
+-----+-----------+
|   1 | 口罩      |
|   2 | 防护服    |
|   3 | 测压机    |
+-----+-----------+
 */
public class Main {
	public static void main(String[] args) {
		Window win = new Window();
		
	}
}
