//
// Created by 刘源峰 on 2021/6/26.
//

#include "course.h"

void courses::readintolist() {
    FILE *fp;
    if ((fp = fopen("course.txt", "r")) == nullptr) {
        return;
    }
    fseek(fp, 0, SEEK_END);   //文件位置指针移动到文件末尾。
    if (ftell(fp) <= 0) {
        return;
    }
    rewind(fp);  //文件位置指针移动到文件开始位置。
    courseNode c{};
    while (fscanf(fp, "%s\t%s\t%lf\t%d\r\n", c.cno, c.cname, &c.credit, &c.time) != -1) {
        list1.push_back(c);
    }
}

void courses::insert(courseNode node) {
    if (find(node.cno) != nullptr) {
        printf("该课程信息已存在！\n");
    } else {
        list1.push_back(node);
    }
}

void courses::insert() {
    courseNode c{};
    printf("输入课程号 课程名称 学分 学时（用空格分割）:");
    scanf("%s %s %lf %d", c.cno, c.cname, &c.credit, &c.time);
    if (find(c.cno) != nullptr) {
        printf("该课程信息已存在！\n");
    } else {
        list1.push_back(c);
    }
}

courseNode *courses::find(char *cno) {
    if (cno == nullptr) {
        return nullptr;
    }
    std::list<courseNode>::iterator it = std::find_if(list1.begin(), list1.end(), findcno(cno));
    courseNode *c = (courseNode *) malloc(sizeof(struct course));
    if (it == list1.end()) {//迭代器尾=链表尾
        return nullptr;
    } else {
        strcpy(c->cno, it->cno);
        strcpy(c->cname, it->cname);
        c->time = it->time;
        c->credit = it->credit;
        return c;
    }
}

void courses::delete_c(char *cno) {
    std::list<courseNode>::iterator it = std::find_if(list1.begin(), list1.end(), findcno(cno));
    if (it != list1.end()) {
        list1.erase(it);//移除结点
    }
}

void courses::update(char *cno) {
    std::list<courseNode>::iterator it = std::find_if(list1.begin(), list1.end(), findcno(cno));
    char s[20];
    if (it != list1.end()) {
        printf("输入新的课程名称 学分 学时（用空格分割）:");
        scanf("%s", s);
        if (s[0] != 0) {
            strcpy(it->cname, s);
        }
        scanf("%s", s);
        if (s[0] != 0) {
            it->credit = atof(s);
        }
        scanf("%s", s);
        if (s[0] != 0) {
            it->time = atoi(s);
        }
    }
}

void courses::print_list() {
    std::list<courseNode>::iterator it;
    if (list1.size() != 0) {
        printf("课程号\t\t课程名称\t\t\t学分\t\t学时\n");
        for (it = list1.begin(); it != list1.end(); it++) {
            printf("%s\t%-20s\t%5.1f%5d\n", it->cno, it->cname, it->credit, it->time);
        }
    }
}

void courses::writetofile() {
    std::list<courseNode>::iterator it;
    FILE *fp;
    if ((fp = fopen("course.txt", "w")) == nullptr) {
        printf("未能对课程信息文件操作\n");
        return;
    }
    for (it = list1.begin(); it != list1.end(); it++) {
        fprintf(fp, "%s\t%s\t%f\t%d\r\n", it->cno, it->cname, it->credit, it->time);
    }
    fflush(fp);
//    fsync(fileno(fp));
            _commit(_fileno(fp));//获取文件描述符后强制写硬盘
    fclose(fp);
}