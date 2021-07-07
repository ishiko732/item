//
// Created by 刘源峰 on 2021/6/26.
//

#ifndef SMS_TREE_COURSE_H
#define SMS_TREE_COURSE_H

#include <list>
#include <unistd.h>
#include <cstdlib>
#include <cstdio>
#include <iostream>
#include "student.h"
#include <algorithm>

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
    void insert(courseNode node);//插入结点

    void insert();//提示插入

    courseNode *find(char *cno);//查找，课程号，课程名

    void delete_c(char *cno);//删除

    void update(char *cno);//更新

    void sort_c() {//排序
        list1.sort(comnode());
    }

    void print_list();//输出课程列表

    void writetofile();//写到文件

    void readintolist();//导入到list

    char **getCnos(char **cnames) {//获取课程号数组
        char **cnos = (char **) malloc(20 * sizeof(char *));
        if (cnos == nullptr) {
            printf("分配空间失败");
            return nullptr;
        }
        int i = 0;
        std::list<courseNode>::iterator it;
        if (list1.size() != 0) {
            for (i = 0, it = list1.begin(); it != list1.end(); it++, i++) {
                cnos[i] = (char *) malloc(sizeof(char) * 20);
                strcpy(cnos[i], it->cno);
                cnames[i] = (char *) malloc(sizeof(char) * 20);
                strcpy(cnames[i], it->cname);
            }
        }
        return cnos;
    }

private:
    std::list<courseNode> list1;
};

#endif //SMS_TREE_COURSE_H
