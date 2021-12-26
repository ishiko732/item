
select d3ID,u.uid,u.name,s.basePay,s.subsidy,s.pi,s.mi,s.ui,s.housingFund,s.total_Salary,p.bounty,p.penalty,registrantTime,statusID
from dep3
join user as u on d3ID=(select fid from position where pid=u.pid)
join salary_view as s on salaryId=(select salaryId from position where s.salaryId=position.salaryId)
join position as p on u.pid = p.pid
join recheckUser as ru on ru.uid=u.uid
order by u.uid;


select d1Name,D2Name,d3Name,count(*) as count,sum(total_Salary)+sum(bounty)+sum(penalty) as sum
from dep3
join (select d3ID,u.uid,u.name,s.basePay,s.subsidy,s.pi,s.mi,s.ui,s.housingFund,s.total_Salary,p.bounty,p.penalty,registrantTime,statusID
        from dep3
       join user as u on d3ID=(select fid from position where pid=u.pid)
       join salary_view as s on salaryId=(select salaryId from position where s.salaryId=position.salaryId)
       join position as p on u.pid = p.pid
       join recheckUser as ru on ru.uid=u.uid
        order by u.uid) as temp
on dep3.d3ID=temp.d3ID
where statusID=1 and DATEDIFF(current_time,registrantTime)>31
group by temp.d3ID;

#判断时间是否够31天
select registrantTime,DATEDIFF(current_time,registrantTime)>31
from user;



#依据user查找部门
select p.fid,count(*) as people
from user
left join recheckUser rU on user.uid = rU.uid
left join position p on user.pid = p.pid
group by p.pid;

#获取薪酬列表
# noinspection NonAsciiCharacters

select serial.payrollID,group_concat(serialID) as serials,d1Name,D2Name,d3Name,count(*) as 'count',sum(total_Salary)+sum(bounty)-sum(penalty) as 'sum',msg as status
from serial
         join user u on u.uid = serial.uid
         join dep3 on d3ID=( select fid from position where pid =u.pid)
         join salary_view on salaryId in (select salaryId from position where u.pid=position.pid)
         join recheckSerial rS on serial.payrollID = rS.payrollID
         join status s on rS.statusID = s.statusID
where serial.statusId=(select statusId from status where msg='发放')
group by payrollID;

#获取薪酬详细内容
select payrollID,serialID,u.uid,u.name,basePay, subsidy, pi, mi, ui, housingFund, total_Salary,bounty,penalty,salaryId
from serial
join user u on u.uid = serial.uid
join salary_view on salaryId=(select salaryId from position where u.pid=position.pid)
where payrollID='SG-202112000008' and statusId=(select statusId from status where msg='发放');


#更新薪酬中的奖金和罚金
update serial
set bounty=100,penalty=200
where serialID=6;


#删除某人的发放
update serial
set statusId=(select statusId from status where msg='不发放')
where serialID=6;

#获取薪酬发放状态
select msg as status
from serial
         join recheckSerial rS on serial.payrollID = rS.payrollID
         join status s on rS.statusID = s.statusID
where serial.serialID=6;
