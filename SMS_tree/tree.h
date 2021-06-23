//
// Created by 刘源峰 on 2021/6/21.
//

#ifndef SMS_TREE_TREE_H
#define SMS_TREE_TREE_H
#include "student.h"
typedef struct stuNode{//student tree structure
    stu *student;
    stuNode *lchild,*rchild;
    int height;
}stuNode;

class tree {
public:
    int height(stuNode *root1);
    stuNode * tree_node_LL(stuNode *root1);
    stuNode * tree_node_RR(stuNode *root1);
    stuNode * tree_node_LR(stuNode *root1);
    stuNode * tree_node_RL(stuNode *root1);
    stuNode * createStuNode(struct stu *student);
    stuNode * insert(stuNode *root1,stuNode *stu);
    stuNode * insert(stuNode *root1,stuNode *stu,stuNode *e);
    stuNode * find(stuNode *root1,char * sno);
    stuNode * remove(stuNode *root1,char *sno);
    void update(stuNode *root1,stu *stu,int updateTofile);
    void print(stuNode *root1);
    void print(stuNode *root1,int writeTofile);
    void writeToFile(struct stu *stu);
    void writeToFileALL(stuNode *root1);
private:

};


#endif //SMS_TREE_TREE_H
