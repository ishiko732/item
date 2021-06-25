//
// Created by 刘源峰 on 2021/6/26.
//

#ifndef SMS_TREE_FILES_H
#define SMS_TREE_FILES_H


#include "tree.h"
#include "student.h"
#include <iostream>
#include <cstring>
//#include <io.h>
#include <unistd.h>

class files_student {
public:
    files_student(stuNode *root, int *pos) {
        this->root = root;
        this->pos_extends = pos;
    }

    int getpos() {
        return *pos_extends;
    }

    stuNode *readfile(tree *t);

    void findfromfile(int count, char *message);

    int deletetofile(tree *t, char *sno, stuNode *root);

private:
    stuNode *root;
    int *pos_extends;

    void pos_stuNode(stuNode *s);
};


#endif //SMS_TREE_FILES_H
