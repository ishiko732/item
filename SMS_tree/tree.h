//
// Created by 刘源峰 on 2021/6/21.
//

#ifndef SMS_TREE_TREE_H
#define SMS_TREE_TREE_H

#include "student.h"
#include "course.h"
#include <cstdlib>
#include <cstdio>
#include <cstring>
//#include <io.h>
#include <unistd.h>

typedef struct stuNode {//student tree structure
    stu *student;
    stuNode *lchild, *rchild;
    int height;
} stuNode;

class tree {
public:
    int height(stuNode *root1);//求高度

    stuNode *tree_node_LL(stuNode *root1);//左单旋

    stuNode *tree_node_RR(stuNode *root1);//右单旋

    stuNode *tree_node_LR(stuNode *root1);//左右双旋

    stuNode *tree_node_RL(stuNode *root1);//右左双旋

    stuNode *createStuNode(struct stu *student);//创建学生信息结点

    stuNode *insert(stuNode *root1, stuNode *stu);//插入

    stuNode *insert(stuNode *root1, stuNode *stu, stuNode *e);//override

    stuNode *find(stuNode *root1, char *sno);//查找

    stuNode *remove(stuNode *root1, char *sno);//删除结点

    void update(stuNode *root1, stu *stu, int updateTofile);//更新

    void print(stuNode *root1);//输出单结点

    void print(stuNode *root1, int writeTofile);//输出并写到文件

    void print_ALL(stuNode *root1, student *sc, courses *c);//输出所有信息，包括成绩

    void writeToFile(struct stu *stu);//学生信息写到文件

    void writeToFileALL(stuNode *root1);//写出所有结点信息到文件

private:

};


#endif //SMS_TREE_TREE_H
