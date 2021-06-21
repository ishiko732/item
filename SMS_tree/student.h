//
// Created by 刘源峰 on 2021/6/21.
//

#ifndef SMS_TREE_STUDENT_H
#define SMS_TREE_STUDENT_H
struct stu{//student message 学号，姓名，性别，年龄，籍贯，专业
    char sno[20];
    char name[20];
    char sex[4];
    int age;
    char region[20];
    char pro[20];
    char addcount;//增加的字段个数
    char *message;//内容
};


class student {

};


#endif //SMS_TREE_STUDENT_H
