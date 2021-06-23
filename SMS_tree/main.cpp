#include "tree.h"
#include "student.h"
#include <iostream>

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wformat"
#pragma ide diagnostic ignored "cert-err34-c"
stuNode *root = nullptr, *new_node = nullptr;

//indexstudent createIndex(struct stu stu, indexstudent e, int indexcount) {
//    memcpy(e.key, stu.sno, sizeof(stu.sno));
//    e.pos = indexcount * sizeof(struct stu);
//    return e;
//}
//
//indexstudent insertIndex(struct stu stu, indexstudent e) {
//    fseek(fd, 0, SEEK_END);
//    memcpy(e.key, stu.sno, sizeof(stu.sno));
//    e.pos = ftell(fd);
//    return e;
//}
//
//void deleteIndex(tree *t, struct stu stu, stuNode *root) {
//    int pos = -1;
//    stuNode *sno_exists = t->find(root, stu.sno);
//    pos = sno_exists->student->index.pos;
//    char front[pos];
//    if (pos >= 0) {
//        fseek(fd, 0, SEEK_END);
//        unsigned int backlen = ftell(fd) - pos - sizeof(struct stu);
//        fseek(fd, 0, SEEK_SET);
//        fread((char *) &front, pos, 1, fd);//读取到该结点处
//        char back[backlen];
//        fseek(fd, sizeof(struct stu), SEEK_CUR);//偏移一个学生对象
//        fread((char *) &back, backlen, 1, fd);//读取到该结点处
//        //修改信息
//        fseek(fd, 0, SEEK_SET);
//        fseek(fd, pos + sizeof(struct stu), SEEK_SET);
//        fwrite((char *) &back, backlen, 1, fd);
////        fwrite((char *)&back,backlen,1,fd);
//    }
//}

void readfile(tree *t) {
    FILE *fp;
    int stuAmount=0;
    struct stu *s = (struct stu *) malloc(sizeof(struct stu));
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
            s->pos = (int)(stuAmount * sizeof(struct stu));
            printf("sno:%s,pos:%d\n",s->sno,s->pos);
            new_node = t->createStuNode(s);
            if(root== nullptr){
                root = t->insert(root, new_node);
            }else{
                root=t->insert(root, new_node);
            }
        }
    }
    fclose(fp);
    printf("读取到%d条学生信息\n", stuAmount);
}
void pos_stuNode(stuNode *s){
    if(s->lchild!= nullptr){
        pos_stuNode(s->lchild);
    }
    s->student->pos-=sizeof(struct stu);
    if(s->rchild!= nullptr){
        pos_stuNode(s->rchild);
    }
}

int deletetofile(tree *t,char * sno){
    FILE *fp;
    int pos = -1;
    printf("%s",sno);
    stuNode *sno_exists = t->find(root, sno);
    if(sno_exists== nullptr){
        printf("未能找到该学生信息！\n");
        return 0;
    }

    pos = sno_exists->student->pos;
    char *front=(char *)malloc(sizeof(char)*pos);
    if ((fp = fopen("student.txt", "r")) == nullptr) {
        printf("文件操作异常！\n");
        //insert;
        return 0;
    }
    fseek(fp, 0, SEEK_END); //文件位置指针移动到文件结束位置
    unsigned int backlen = ftell(fp) - pos - sizeof(struct stu);
    char *back=(char *)malloc(sizeof(char)*backlen);
    fseek(fp, 0, SEEK_SET);
    fread(front,pos,1,fp);//front buf
    //读取到该结点处
    fseek(fp, sizeof(struct stu), SEEK_CUR);//偏移一个学生对象
    fread(back, backlen, 1, fp);//读取被删除结点的后面信息
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
    fclose(fp);
    return 1;
}

int main() {
//    system("chcp 65001 > nul");
    int choose, input_value;
    char sno[20];
    struct stu stu{};//定义一个学生结构体类型的数据用来缓存学生数据
    stuNode *find_node;
    tree *t = new tree();
    //初始化一个节点root为空
    //导入文件到树
    readfile(t);

    while (1) {
        printf("****************欢迎来到学生成绩管理系统************\n");
        printf("请输入数字选择相应的指令\n");
        printf("1、增加插入学生的成绩\n");
        printf("2、搜索学生成绩\n");
        printf("3、打印所有学生的成绩\n");
        printf("4、删除学生的成绩\n");
        printf("5、退出学生成绩管理系统\n");
        printf("*****************************************************\n");

        scanf("%d", &choose);
        switch (choose) {
            case 1:
                printf("请您输入学生信息：学号，姓名，性别，年龄，籍贯，专业\n");
                scanf("%s %s %s %d %s %s",
                      &stu.sno, &stu.name, &stu.sex, &stu.age, &stu.region, &stu.pro);
                new_node = t->createStuNode(&stu);
                if(root== nullptr){
                    root = t->insert(root, new_node);
                }else{
                    root=t->insert(root, new_node);
                }
                break;
            case 2:
                printf("请输入学生的学号来查找学生信息\n");
                scanf("%s", &sno);

                find_node = t->find(root, sno);

                if (find_node == nullptr) {
                    printf("找不到该学生的信息\n");
                    break;
                }else{
                    printf("学号：%s,学生姓名：%s,性别：%s,年龄：%d，地区：%s，专业：%s\n",
                           find_node->student->sno, find_node->student->name, find_node->student->sex,find_node->student->age, find_node->student->region,
                           find_node->student->pro);
                }
                break;
            case 3:
                printf("所有的学生的信息为\n");
                t->print(root);
                break;
            case 4:
                printf("请输入学生的编号来删除学生成绩信息\n");
                scanf("%s", &sno);
                if(deletetofile(t,sno)){
                    root = t->remove(root, sno);
                }
                break;

            case 5:
                t->print(root, 1);
                goto log_out;

        }


    }
    log_out:
    return 0;
}

#pragma clang diagnostic pop