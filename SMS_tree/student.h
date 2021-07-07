//
// Created by 刘源峰 on 2021/6/21.
//

#ifndef SMS_TREE_STUDENT_H
#define SMS_TREE_STUDENT_H

#include <list>
#include <unistd.h>

struct stu {//student message 学号，姓名，性别，年龄，籍贯，专业
    char sno[20];
    int pos;//偏移量
    char name[20];
    char sex[4];
    int age;
    char region[20];
    char pro[20];
    //char addcount;//增加的字段个数
    //char *message;//内容
};


typedef struct course {//课程信息
    char cno[20];
    char cname[20];
    double credit;
    int time;
} courseNode;

typedef struct score {//成绩信息
    char sno[20];
    char cno[20];
    double grade;
} scoreNode;


class findgrade {//STL查找corse是否含有
public:
    findgrade(char *sno, char *cno) {
        this->sno = sno;
        this->cno = cno;
    }

    bool operator()(scoreNode &sc) {
        return strcmp(sc.cno, cno) == 0 and strcmp(sc.sno, sno) == 0;
    }

private:
    char *sno, *cno;
};

struct comnode_sc {
    bool operator()(scoreNode &a, scoreNode &b) {//比较器
        int ret = strcmp(a.sno, b.sno);
        if (ret == 0) {
            ret = strcmp(a.cno, b.cno);
        }
        return ret < 0;
    }
};

class student {
public:
    void insert(scoreNode node);//插入sc

    double find(char *sno, char *cno);//查找

    void delete_sc(char *sno, char *cno);//删除

    void update(char *sno, char *cno, double grade);//更新

    void sort_c() {//排序
        list1.sort(comnode_sc());
    }

    void writetofile();//写到文件

    void readintolist();//导入到list

    void print_list();//输出sc(sno,cno,grade) 不启用

private:
    std::list<scoreNode> list1;
};
#endif //SMS_TREE_STUDENT_H
