//
// Created by 刘源峰 on 2021/6/21.
//

#include <cstdlib>
#include <cstdio>
#include <cstring>
#include "tree.h"

#define MAX(A, B) ((A)>(B)?(A):(B))


int tree::height(stuNode *root1) {//tree height
    if (root1 == nullptr) {
        return 0;
    }
    return (MAX(height(root1->lchild), height(root1->rchild))) + 1;
}

stuNode *tree::tree_node_LL(stuNode *root1) {
    stuNode *tmp;
    tmp = root1->lchild;
    root1->lchild = tmp->rchild;
    tmp->rchild = root1;

    tmp->height = height(tmp);
    root1->height = height(root1);

    return tmp;
}

stuNode *tree::tree_node_RR(stuNode *root1) {
    stuNode *tmp;
    tmp = root1->rchild;
    root1->rchild = tmp->lchild;
    tmp->lchild = root1;

    tmp->height = height(tmp);
    root1->height = height(root1);

    return tmp;
}

stuNode *tree::tree_node_LR(stuNode *root1) {//R->L
    root1->lchild = tree_node_RR(root1->lchild);
    root1 = tree_node_LL(root1);
    return root1;
}

stuNode *tree::tree_node_RL(stuNode *root1) {//L->R
    root1->rchild = tree_node_LL(root1->rchild);
    root1 = tree_node_RR(root1);
    return root1;
}

stuNode *tree::createStuNode(struct stu *student) {
    stuNode *node;
    stu *stu = (struct stu *) malloc(sizeof(struct stu));
    stu = (struct stu *) memcpy(stu, student, sizeof(struct stu));
    node = (stuNode *) (malloc(sizeof(stuNode)));
    if (node == nullptr) {
        printf("未能申请到二叉树结点\n");
        return nullptr;
    } else {
        node->rchild = nullptr;
        node->lchild = nullptr;
        node->student = stu;
    }
    return node;
}

stuNode *tree::insert(stuNode *root1, stuNode *stu) {
    int x;
    if (root1 == nullptr) {
        root1 = stu;
    } else {
        stuNode *e = find(root1, stu->student->sno);
        if (e != nullptr) {
            printf("插入有相同的学号！拒绝插入！\n");
            return root1;
        } else {
            x = strcmp(stu->student->sno, root1->student->sno);
            //<0 插入元素小于根结点元素
            //>0 插入元素大于根结点元素
            if (x < 0) {//元素小于根结点,插入左边
                root1->lchild = insert(root1->lchild, stu);

                if (height(root1->lchild) - height(root1->rchild) == 2) {//不平衡
                    if (x < 0) {//左左旋转
                        root1 = tree_node_LL(root1);
                    } else {//左右旋转
                        root1 = tree_node_LR(root1);
                    }
                }

            } else if (x > 0) {//元素大于根结点，插入右边
                root1->rchild = insert(root1->rchild, stu);

                if (height(root1->rchild) - height(root1->lchild) == 2) {//不平衡
                    if (x > 0) {//右右旋转
                        root1 = tree_node_RR(root1);
                    } else {//左右旋转
                        root1 = tree_node_RL(root1);
                    }
                }
            }
        }
    }
    root1->height = height(root1);
    return root1;
}

stuNode *tree::insert(stuNode *root1, stuNode *stu, stuNode *e) {
    int x;
    if (root1 == nullptr) {
        root1 = stu;
    } else {
        if (e != nullptr) {
            printf("插入有相同的学号！拒绝插入！\n");
            return root1;
        } else {
            x = strcmp(stu->student->sno, root1->student->sno);

            if (x < 0) {//元素小于根结点,插入左边
                root1->lchild = insert(root1->lchild, stu);

                if (height(root1->lchild) - height(root1->rchild) == 2) {//不平衡
                    if (x < 0) {//左左旋转
                        root1 = tree_node_LL(root1);
                    } else {//左右旋转
                        root1 = tree_node_LR(root1);
                    }
                }

            } else if (x > 0) {//元素大于根结点，插入右边
                root1->rchild = insert(root1->rchild, stu);

                if (height(root1->rchild) - height(root1->lchild) == 2) {//不平衡
                    if (x > 0) {//右右旋转
                        root1 = tree_node_RR(root1);
                    } else {//左右旋转
                        root1 = tree_node_RL(root1);
                    }
                }
            }
        }
    }
    root1->height = height(root1);
    return root1;
}

stuNode *tree::find(stuNode *root1, char *sno) {//搜索查询学生信息
    if (root1 == nullptr) {
        return nullptr;
    }
    int x = strcmp(root1->student->sno, sno);//0 相等 学号大 <0，结点大 >0
    if (x > 0) {
        return find(root1->lchild, sno);
    } else if (x < 0) {
        return find(root1->rchild, sno);
    }
    return root1;
}

void tree::print(stuNode *root1) {
    if (root1 == nullptr) {
        return;
    }
    print(root1->lchild);
    printf("学号：%s,学生姓名：%s,性别：%s,年龄：%d，地区：%s，专业：%s 偏移量:%d\n",
           root1->student->sno, root1->student->name, root1->student->sex, root1->student->age,
           root1->student->region, root1->student->pro, root1->student->pos);
    print(root1->rchild);
}

void tree::writeToFile(struct stu *stu) {
    FILE *fp;
    if ((fp = fopen("student.txt", "a")) != nullptr)//追加信息
    {
        if (fwrite(stu, sizeof(struct stu), 1, fp) != 1) {
            printf("写入文件错误！\n");
        }
        fclose(fp);
    }
}

void tree::writeToFileALL(stuNode *root1) {
    FILE *fp;
    fp = fopen("student.txt", "w");
    fclose(fp);
    print(root1, 1);
}

void tree::print(stuNode *root1, int writeTo) {
    if (root1 == nullptr) {
        return;
    }
    print(root1->lchild, writeTo);
    if (writeTo == 0) {
        printf("学号：%s,学生姓名：%s,性别：%s,年龄：%d，地区：%s，专业：%s 偏移量:%d\n",
               root1->student->sno, root1->student->name, root1->student->sex, root1->student->age,
               root1->student->region, root1->student->pro, root1->student->pos);
    } else {
        writeToFile(root1->student);
    }
    print(root1->rchild, writeTo);
}

stuNode *tree::remove(stuNode *root1, char *sno) {//删除学生成绩信息
    stuNode *tmp;

    if (root1 == nullptr) {
        return nullptr;
    }

    int x = strcmp(root1->student->sno, sno);//0 相等 学号大 <0，结点大 >0

    if (x == 0) {
        if (root1->lchild == nullptr && root1->rchild == nullptr) {
            free(root1->student);
            free(root1);
            return nullptr;
        } else if (root1->lchild != nullptr)//如果有左子树
        {
            for (tmp = root1->lchild; tmp->rchild != nullptr; tmp = tmp->rchild);//用tmp来记录root的左子树当中的最右边的节点（也就是左树当中的最大值）
            struct stu *swap = tmp->student; //交换结点
            tmp->student = root1->student;
            root1->student = swap;
            root1->lchild = remove(root1->lchild, sno);//递归的删除掉重复出来的这个节点,将左子树的数据更新进来
        } else//如果只有右子树
        {
            for (tmp = root1->rchild; tmp->lchild != nullptr; tmp = tmp->lchild);//用tmp来记录root的右子树中的最左边的节点（也就是有树当中的最小值）
            struct stu *swap = tmp->student;//交换结点
            tmp->student = root1->student;
            root1->student = swap;
            root1->rchild = remove(root1->lchild, sno);//递归的删除掉重复出来的这个节点,将右子树的数据更新进来
        }
    } else if (x > 0)//如果删除的数据比这个节点要小，则继续往左边去删除节点
    {
        root1->lchild = remove(root1->lchild, sno);
    } else//如果删除的数据比这个节点要大，则继续往右边去删除
    {
        root1->rchild = remove(root1->lchild, sno);
    }

    //插入新节点结束后再判断是否出现不平衡


    int h = height(root1->lchild) - height(root1->rchild);
    x = strcmp(root1->student->sno, sno);
    if (h == 2)//左不平衡
    {
        //如果插入的数据比跟的左树节点的数据要小，那他肯定是插入到root->lchild的左边去，出现了左左不平衡

        if (x > 0)//左左不平衡
        {
            root1 = tree_node_LL(root1);
        } else//否则则是插入到root->lchild的右边去，出现了左右不平衡
        {
            root1 = tree_node_LR(root1);
        }
    } else if (h == -2)//右不平衡
    {

        if (x <= 0)//右右不平衡
        {
            //printf("出现右不平衡\n");
            root1 = tree_node_RR(root1);
        } else//右左不平衡
        {
            root1 = tree_node_RL(root1);
        }
    }
    root1->height = height(root1);
    return root1;//如果当前root这个节点不是我们删除的节点，我们便原封不动的返回出去
}

void tree::update(stuNode *root1, stu *stu, int isUpdate) {
    stuNode *find_node = find(root1, stu->sno);
    if (find_node == nullptr) {
        printf("您所选择的学生信息不存在！");
        return;
    }

    struct stu *s=find_node->student;
    strcpy(s->name,stu->name);
    strcpy(s->pro,stu->pro);
    strcpy(s->region,stu->region);
    s->age=stu->age;
    strcpy(s->sex,stu->sex);
    if (isUpdate) {
        int frontlen;
        FILE *fp;
        frontlen = find_node->student->pos;
        char *front = (char *) malloc(sizeof(char) * frontlen);
        if ((fp = fopen("student.txt", "r")) == nullptr) {
            printf("文件操作异常！\n");
            return;
        }
        fseek(fp, 0, SEEK_END); //文件位置指针移动到文件结束位置
        unsigned int backlen = ftell(fp) - frontlen - sizeof(struct stu);
        char *back = (char *) malloc(sizeof(char) * backlen);
        fseek(fp, 0, SEEK_SET);
        fread(front, frontlen, 1, fp);//front buf
        //读取到该结点处
        fseek(fp, sizeof(struct stu), SEEK_CUR);//偏移一个学生对象
        fread(back, backlen, 1, fp);//读取要更新结点的后面信息
        fclose(fp);
        if(front[0]!=0 or back[0]!=0 ) {
            if ((fp = fopen("student.txt", "w")) == nullptr) {//覆盖文件
                printf("文件操作异常！\n");
                return;
            }
            fseek(fp, 0, SEEK_SET);

            fwrite(front, frontlen, 1, fp);
            fwrite(stu, sizeof(struct stu), 1, fp);
            fwrite(back, backlen, 1, fp);

            free(front);
            free(back);
            fclose(fp);
        }
        return;
    }
}
