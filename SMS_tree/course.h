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

    char **getCnos(char **cnames) {
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
