//
// Created by 刘源峰 on 2021/6/21.
//

#ifndef SMS_TREE_TREE_H
#define SMS_TREE_TREE_H
#include "student.h"
typedef struct stuNode{//student tree structure
    struct stu *student;
    struct stuNode *lchild,*rchild;
    int height;
}stuNode;

class tree {
public:
    tree();
    int height(stuNode *root1);
    stuNode * tree_node_LL(stuNode *root1);
    stuNode * tree_node_RR(stuNode *root1);
    stuNode * tree_node_LR(stuNode *root1);
    stuNode * tree_node_RL(stuNode *root1);
    stuNode * createStuNode(struct stu *student);
    stuNode * insert(stuNode *root1,stuNode *stu);
    stuNode * find(stuNode *root1,char * sno);
    stuNode * remove(stuNode *root1,char *sno);
    void print(stuNode *root1);
private:
    stuNode *root;
    stuNode * setroot(struct stu *stu);
};


#endif //SMS_TREE_TREE_H
