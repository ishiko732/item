#include "tree.h"
#include "student.h"
#include <iostream>
#include <cstring>
#include <io.h>
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wformat"
#pragma ide diagnostic ignored "cert-err34-c"
stuNode *root = nullptr;
int pos_extends=0;
void readfile(tree *t) {
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
            pos_extends=s->pos;
            new_node = t->createStuNode(s);
            printf("sno:%s,pos:%d\n",s->sno,s->pos);
            root = t->insert(root, new_node);
        }
        printf("读取到%d条学生信息\n", stuAmount);
    }
    free(s);
    fflush(fp);
    _commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
}

void pos_stuNode(stuNode *s) {
    if (s->lchild != nullptr) {
        pos_stuNode(s->lchild);
    }
    s->student->pos -= sizeof(struct stu);
    if (s->rchild != nullptr) {
        pos_stuNode(s->rchild);
    }
}

int deletetofile(tree *t, char *sno) {
    FILE *fp;
    int pos;
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
    _commit(_fileno(fp));//获取文件描述符后强制写硬盘
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
    _commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
    return 1;
}
void findfromfile(int count,char *message) {//基于寻找文件信息来定下sno，再传递给find
    FILE *fp;
    if ((fp = fopen("student.txt", "r")) == nullptr) {
        return ;
    }
    int stuAmount;
    stu *s = (struct stu*) malloc(sizeof(struct stu));
    fseek(fp, 0, SEEK_END);   //文件位置指针移动到文件末尾。
    if (ftell(fp) > 0) //文件不为空。
    {
        rewind(fp);  //文件位置指针移动到文件开始位置。
        char *find;
        for (stuAmount = 0; !feof(fp) && fread(s, sizeof(struct stu), 1, fp);) {//信息读取
            if(strstr(s->sno,message) or strstr(s->name,message) or strstr(s->sex,message) or (s->age==atoi(message)) or strstr(s->region,message) or strstr(s->pro,message)){
                printf("学号：%s 学生姓名：%s 性别：%s 年龄：%d 地区：%s 专业：%s\n",
                       s->sno, s->name, s->sex, s->age,s->region, s->pro);
                stuAmount++;
            }
            if(count!=0 && stuAmount>count){
                break;
            }
        }
    }
    printf("读取到%d条学生信息\n", stuAmount);
    free(s);
    fflush(fp);
    _commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
}

stu input_student() {
    stu stu{};//定义一个学生结构体类型的数据用来缓存学生数据
    printf("请您输入学生信息：学号 姓名 性别 年龄 籍贯 专业(用空格分开)\n");//其中学号为12位（限定）
    scanf("%s %s %s %d %s %s",
          &stu.sno, &stu.name, &stu.sex, &stu.age, &stu.region, &stu.pro);

    int count=0;
    for (int i = 0; stu.sno[i]!=0; i++) {//学号合法性。判断是否为数字，12位，前4位是否正确年份
        if ((stu.sno[i] >= '0' && stu.sno[i] <= '9')) {
            count+=1;
        }
    }
    if (count==0){
        printf("学号不能为空！");
    }else if(count==12){
        if(stu.sno[0]>'2' or stu.sno[1]>'0' or stu.sno[2]>'2'){
            printf("学号的年份出错！");
        }
    }

    if(strcmp(stu.sex,"男")!=0 or strcmp(stu.sex,"女")!=0){
        printf("性别出错！");
    }
    if(stu.age<0 or stu.age>100){
        printf("年龄出错！");
    }
    return stu;
}

int main() {
//    system("chcp 65001 > nul");
    int choose;
    char sno[20];
    struct stu stu{};//定义一个学生结构体类型的数据用来缓存学生数据
    stuNode *find_node,*new_node;
    tree *t = new tree();
    //初始化一个节点root为空
    //导入文件到树
    readfile(t);

    while (1) {
        printf("****************欢迎来到学生成绩管理系统************\n");
        printf("请输入数字选择相应的指令\n");
        printf("1、增加插入学生的信息\n");
        printf("2、搜索学生信息\n");
        printf("3、模糊查找学生\n");
        printf("4、打印所有学生的信息\n");
        printf("5、删除学生的信息\n");
        printf("6、更新学生的信息\n");
        printf("7、退出学生信息管理系统\n");
        printf("*****************************************************\n");

        scanf("%d", &choose);
        switch (choose) {
            case 1:
                printf("请您输入学生信息：学号 姓名 性别 年龄 籍贯 专业\n");
                scanf("%s %s %s %d %s %s",
                      &stu.sno, &stu.name, &stu.sex, &stu.age, &stu.region, &stu.pro);
                pos_extends+=sizeof(stu);
                stu.pos=pos_extends;
                new_node = t->createStuNode(&stu);
                if ((find_node = t->find(root, stu.sno)) == nullptr) {
                    t->writeToFile(&stu);
                }
                root = t->insert(root, new_node, find_node);
                break;
            case 2:
                printf("请输入学生的学号来查找学生信息\n");
                scanf("%s", &sno);

                find_node = t->find(root, sno);

                if (find_node == nullptr) {
                    printf("找不到该学生的信息\n");
                    break;
                } else {
                    printf("学号：%s,学生姓名：%s,性别：%s,年龄：%d，地区：%s，专业：%s\n",
                           find_node->student->sno, find_node->student->name, find_node->student->sex,
                           find_node->student->age, find_node->student->region,
                           find_node->student->pro);
                }
                break;
            case 3:
                printf("请输入信息：\n");
                char s[1024];
                scanf("%s",&s);
                findfromfile(0,s);
//                t->print(root);
                break;
            case 4:
                printf("所有的学生的信息为\n");
                t->print(root);
                break;
            case 5:
                printf("请输入学生的编号来删除学生信息\n");
                scanf("%s", &sno);
                if (deletetofile(t, sno)) {
                    root = t->remove(root, sno);
                }
                break;
            case 6:
                printf("请输入更新后的学生信息（以学号作为关键）\n学号 姓名 性别 年龄 籍贯 专业\n");
                scanf("%s %s %s %d %s %s",
                      &stu.sno, &stu.name, &stu.sex, &stu.age, &stu.region, &stu.pro);

                t->update(root,&stu,1);
                break;
            case 7:
                t->writeToFileALL(root);
                return 0;
        }
    }
}

#pragma clang diagnostic pop