#include "tree.h"
#include "student.h"
#include "files.h"
#include <iostream>
#include <cstring>
#include "course.h"
//#include <io.h>
#include <unistd.h>

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wformat"
#pragma ide diagnostic ignored "cert-err34-c"
stuNode *root = nullptr;
int pos_extends = 0;

int input_student(struct stu *stu) {
    int ret = 1;
    printf("请您输入学生信息：学号 姓名 性别 年龄 籍贯 专业(用空格分开)\n");//其中学号为12位（限定）
    scanf("%s %s %s %d %s %s",
          &stu->sno, &stu->name, &stu->sex, &stu->age, &stu->region, &stu->pro);

    int count = 0;
    for (int i = 0; stu->sno[i] != 0; i++) {//学号合法性。判断是否为数字，12位，前4位是否正确年份
        if ((stu->sno[i] >= '0' && stu->sno[i] <= '9')) {
            count += 1;
        }
    }
    if (count == 0) {
        printf("学号不能为空！\n");
        ret = 0;
    } else if (count == 12) {
        if (stu->sno[0] > '2' or stu->sno[1] > '0' or stu->sno[2] > '2') {
            printf("学号的年份出错！\n");
            ret = 0;
        }
    }

    if (strcmp(stu->sex, "男") != 0 and strcmp(stu->sex, "女") != 0) {
        printf("性别出错！\n");
        ret = 0;
    }
    if (stu->age < 0 or stu->age > 100) {
        printf("年龄出错！\n");
        ret = 0;
    }
    pos_extends += sizeof(struct stu);
    stu->pos = pos_extends;
    return ret;
}

void test_couse() {
    courses *c = new courses();
    c->readintolist();
//    c->insert();//19221101 高等数学Ⅰ 9.5 152
//    c->insert();//16522105 C++程序设计 4 64
//    printf("%p\n",c->find("高等数学Ⅰ"));
//    printf("%p\n",c->find("16221301"));
//    c->update("19221101");// 高级数学 5 15
    c->print_list();
//    c->sort_c();
//    c->print_list();
//    c->writetofile();

}

void test_sc() {
    student *sc = new student();
    scoreNode scNode1{"201611701208", "19221101", 99};
    scoreNode scNode2{"201611701209", "19221101", 100};
    scoreNode scNode3{"201611301107", "16522105", 60};
    scoreNode scNode4{"201611701201", "16522105", 85};
    sc->insert(scNode1);
    sc->insert(scNode2);
    sc->insert(scNode3);
    sc->insert(scNode4);

    printf("sno:%s cno:%s grade:%.1f\n", "201611701201", "16522105", sc->find("201611701201", "16522105"));
    printf("sno:%s cno:%s grade:%.1f\n", "201611701201", "16522105", sc->find("201611701201", "16522106"));
    sc->delete_sc("201611701201", "16522105");
    sc->writetofile();

}

int main() {
//    test_couse();
//    test_sc();
//    return 0;
    int choose;
    char sno[20];
    struct stu stu{};//定义一个学生结构体类型的数据用来缓存学生数据
    stuNode *find_node, *new_node;
    tree *t = new tree();
    courses *c = new courses();
    student *sc = new student();
    files_student *fs = new files_student(root, &pos_extends);

    root = fs->readfile(t);//读取学生信息文件到树

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
                if (input_student(&stu)) {//获取一个学生信息
                    new_node = t->createStuNode(&stu);
                    if ((find_node = t->find(root, stu.sno)) == nullptr) {
                        t->writeToFile(&stu);
                    }
                    root = t->insert(root, new_node, find_node);
                }
                break;
            case 2:
                printf("请输入学生的学号来查找学生信息\n");
                scanf("%s", &sno);

                find_node = t->find(root, sno);

                if (find_node == nullptr) {
                    printf("找不到该学生的信息\n");
                    break;
                } else {
                    printf("学号：%s 学生姓名：%s 性别：%s 年龄：%d 地区：%s 专业：%s\n",
                           find_node->student->sno, find_node->student->name, find_node->student->sex,
                           find_node->student->age, find_node->student->region,
                           find_node->student->pro);
                }
                break;
            case 3:
                printf("请输入信息：\n");
                char s[1024];
                scanf("%s", &s);
                fs->findfromfile(0, s);
                break;
            case 4:
                printf("所有的学生的信息为\n");
                t->print(root);
                break;
            case 5:
                printf("请输入学生的学号来删除学生信息\n");
                scanf("%s", &sno);
                if (fs->deletetofile(t, sno, root)) {
                    root = t->remove(root, sno);
                }
                break;
            case 6:
                printf("请输入更新后的学生信息（以学号作为关键）\n学号 姓名 性别 年龄 籍贯 专业\n");
                scanf("%s %s %s %d %s %s",
                      &stu.sno, &stu.name, &stu.sex, &stu.age, &stu.region, &stu.pro);

                t->update(root, &stu, 1);
                break;
            case 7:
                t->writeToFileALL(root);
                return 0;
        }
    }
}

#pragma clang diagnostic pop