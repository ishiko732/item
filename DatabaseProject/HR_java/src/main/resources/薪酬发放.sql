
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
from user