//
// Created by 刘源峰 on 2021/6/26.
//

#include "files.h"

stuNode *files_student::readfile(tree *t) {
    FILE *fp;
    int stuAmount;
    stu *s = (struct stu *) malloc(sizeof(struct stu));
    stuNode *new_node;
    if ((fp = fopen("student.txt", "r")) == nullptr)   //判断是否打开文件
    {
        if ((fp = fopen("student.txt", "w")) == nullptr) {
            printf("student.txt 信息表建立失败,退出程序!\n");
            exit(0);
        }
    }
    fseek(fp, 0, SEEK_END);   //文件位置指针移动到文件末尾。
    if (ftell(fp) > 0) //文件不为空。
    {
        rewind(fp);  //文件位置指针移动到文件开始位置。
        for (stuAmount = 0; !feof(fp) && fread(s, sizeof(struct stu), 1, fp); stuAmount++) {//文件导入
            s->pos = (int) (stuAmount * sizeof(struct stu));
            *pos_extends = s->pos;
            new_node = t->createStuNode(s);
            printf("sno:%s,pos:%d\n", s->sno, s->pos);
            root = t->insert(root, new_node);
        }
        printf("读取到%d条学生信息\n", stuAmount);
    }
    free(s);
    fflush(fp);
    fsync(fileno(fp));
//    _commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
    return root;
}


void files_student::findfromfile(int count, char *message) {//基于寻找文件信息来定下sno，再传递给find
    FILE *fp;
    if ((fp = fopen("student.txt", "r")) == nullptr) {
        return;
    }
    int stuAmount;
    stu *s = (struct stu *) malloc(sizeof(struct stu));
    fseek(fp, 0, SEEK_END);   //文件位置指针移动到文件末尾。
    if (ftell(fp) > 0) //文件不为空。
    {
        rewind(fp);  //文件位置指针移动到文件开始位置。
        char *find;
        for (stuAmount = 0; !feof(fp) && fread(s, sizeof(struct stu), 1, fp);) {//信息读取
            if (strstr(s->sno, message) or strstr(s->name, message) or strstr(s->sex, message) or
                (s->age == atoi(message)) or strstr(s->region, message) or strstr(s->pro, message)) {
                printf("学号：%s 学生姓名：%s 性别：%s 年龄：%d 地区：%s 专业：%s\n",
                       s->sno, s->name, s->sex, s->age, s->region, s->pro);
                stuAmount++;
            }
            if (count != 0 && stuAmount > count) {
                break;
            }
        }
    }
    printf("读取到%d条学生信息\n", stuAmount);
    free(s);
    fflush(fp);
    fsync(fileno(fp));
    //_commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
}

void files_student::pos_stuNode(stuNode *s) {
    if (s->lchild != nullptr) {
        pos_stuNode(s->lchild);
    }
    s->student->pos -= sizeof(struct stu);
    if (s->rchild != nullptr) {
        pos_stuNode(s->rchild);
    }
}

int files_student::deletetofile(tree *t, char *sno, stuNode *root) {
    FILE *fp;
    int pos;
    this->root = root;
    stuNode *sno_exists = t->find(root, sno);
    if (sno_exists == nullptr) {
        printf("未能找到该学生信息！\n");
        return 0;
    }

    pos = sno_exists->student->pos;
    char *front = (char *) malloc(sizeof(char) * pos);
    if ((fp = fopen("student.txt", "r")) == nullptr) {
        printf("文件操作异常！\n");
        return 0;
    }
    fseek(fp, 0, SEEK_END); //文件位置指针移动到文件结束位置
    unsigned int backlen = ftell(fp) - pos - sizeof(struct stu);
    char *back = (char *) malloc(sizeof(char) * backlen);
    fseek(fp, 0, SEEK_SET);
    fread(front, pos, 1, fp);//front buf
    //读取到该结点处
    fseek(fp, sizeof(struct stu), SEEK_CUR);//偏移一个学生对象
    fread(back, backlen, 1, fp);//读取被删除结点的后面信息
    fflush(fp);
    fsync(fileno(fp));
    //_commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);

    if ((fp = fopen("student.txt", "w")) == nullptr) {//覆盖文件
        printf("文件操作异常！\n");
        return 0;
    }
    fseek(fp, 0, SEEK_SET);
    fwrite(front, pos, 1, fp);
    fwrite(back, backlen, 1, fp);

    //对子结点偏移修改
    pos_stuNode(sno_exists);
    free(front);
    free(back);
    fflush(fp);
    fsync(fileno(fp));
    //_commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
    return 1;
}
