//
// Created by SoneS on 2021/6/16.
//

#ifndef STUDENTMANAGEMENTSYSTEM_STUDENT_H
#define STUDENTMANAGEMENTSYSTEM_STUDENT_H

#include <iostream>
#include <fstream>
#include <iomanip>

class Student {
    friend class studentMessage;

private:
    std::string ID;
    std::string name;
    double score[5];//1-4代表math、English、OPP和总成绩  ，0号单元空出
    Student *next;
public:
    Student();
    std::string getName();//名字
    std::string getID();//学号
    double getMath();//获取数学成绩
    double getEnglish();//获取英语成绩
    double getCpp();//获取c++成绩
    double gettotal();//总成绩
    void in_Student();
    void display();//打印成绩
};


#endif //STUDENTMANAGEMENTSYSTEM_STUDENT_H
