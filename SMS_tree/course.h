//
// Created by 刘源峰 on 2021/6/26.
//

#ifndef SMS_TREE_COURSE_H
#define SMS_TREE_COURSE_H

#include <list>
#include <unistd.h>
#include "student.h"

class findcno {//STL查找corse是否含有
public:
    findcno(char *message) {
        this->msg = message;
    }

    bool operator()(courseNode &c) {
        return strcmp(c.cno, msg) == 0 or strcmp(c.cname, msg) == 0;
    }

private:
    char *msg;
};

struct comnode {
    bool operator()(courseNode &a, courseNode &b) {//比较器
        return strcmp(a.cno, b.cno) < 0;
    }
};

class courses {
public:
    void insert(courseNode node);

    void insert();

    courseNode *find(char *cno);

    void delete_c(char *cno);

    void update(char *cno);

    void sort_c() {
        list1.sort(comnode());
    }

    void print_list();

    void writetofile();

    void readintolist();

private:
    std::list<courseNode> list1;
};

#endif //SMS_TREE_COURSE_H
