//
// Created by SoneS on 2021/6/16.
//

#ifndef STUDENTMANAGEMENTSYSTEM_STUDENTMESSAGE_H
#define STUDENTMANAGEMENTSYSTEM_STUDENTMESSAGE_H

#include "Student.h"

class studentMessage {
private:
    Student *first;
    Student *last;
public:
    studentMessage();
    void add();//向表中增加学生成绩记录
    void searchId();//用学号在表中查找学生成绩记录
    void searchName();//用名字在表中寻找学生
    void del();//在表中删除学生成绩记录
    void showAll();//输出所有同学所有科目的成绩
    void clear();//清空所有信息并退出系统
    void change();//修改学生的成绩
    void read();//读取文件

};


#endif //STUDENTMANAGEMENTSYSTEM_STUDENTMESSAGE_H
