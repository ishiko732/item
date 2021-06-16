//
// Created by SoneS on 2021/6/16.
//

#include "studentMessage.h"

studentMessage::studentMessage() {
    first = NULL;
    last = NULL;
}

void studentMessage::add() {
    Student *t = new Student;
    Student *p = first;
    t->in_Student();
    while (p) {
        if (p->ID == t->ID) {
            std::cout << "\n学号输入错误或该学生成绩已经存在！(如需修改，请先删除再重新录入)" << std::endl;
            return;
        }
        p = p->next;
    }
    if (first == NULL) {
        first = last = t;

    } else {
        last->next = t;
        last = t;
    }
}

void studentMessage::searchId() {
    std::string a;
    std::cout << "\n请输入要查找的学生的学号:";
    std::cin >> a;
    Student *t = first;
    while (t) {
        if (t->ID == a) {
            break;
        }
        t = t->next;
    }
    if (!t) {
        std::cout << "\n未找到要查找学生！" << std::endl;
        return;
    }
    std::cout << "\n查找成功！" << std::endl;
    std::cout << " 学号     姓名     数学     英语     C++     总成绩" << std::endl;
    t->display();
}

void studentMessage::searchName() {
    std::string a;
    std::cout << "\n请输入要查找的学生的姓名:";
    std::cin >> a;
    Student *t = first;
    while (t) {
        if (t->name == a) {
            break;
        }
        t = t->next;
    }
    if (!t) {
        std::cout << "\n未找到要查找学生！" << std::endl;
        return;
    }
    std::cout << "\n查找成功！" << std::endl;
    std::cout << " 学号     姓名     数学     英语     C++     总成绩" << std::endl;
    t->display();
}

void studentMessage::del() {
    std::string a;
    std::cout << "\n请输入要删除的学生的学号: ";
    std::cin >> a;
    Student *t = first;
    Student *p = nullptr;
    while (t) {
        if (t->ID == a) {
            break;
        }
        p = t;
        t = t->next;
    }
    if (!t) {
        std::cout << "\n未找到要删除学生！" << std::endl;
        return;
    }
    if (!p) {
        first = first->next;
        std::cout << "\n成功删除学生" << a << std::endl;
        delete t;
    } else {
        p->next = t->next;
        std::cout << "成功删除学生" << a << std::endl;
        delete t;
    }


}

void studentMessage::read() {
    std::ifstream file;

    file.open("afile.dat", std::ios::in);

    if (!file.is_open()) {
        std::cout << "文件打开失败" << '\n';
        return;
    }
    char buf[1024] = {0};
    while (file.getline(buf, sizeof(buf))) {
        std::cout << buf << std::endl;
    }
    file.close();
}


void studentMessage::showAll() {
    std::cout << "---------------------成绩列表----------------------" << std::endl;
    std::cout << " 学号    姓名     数学     英语     Cpp     总成绩" << std::endl;
    Student *t = first;
    while (t) {
        t->display();
        t = t->next;
    }
}


void studentMessage::clear() {
    int x;
    std::cout << "确认要清空所有信息？请输入1或2 (1:确认 2:取消) : ";
    std::cin >> x;
    if (x == 2) {
        std::cout << "\n取消清空\n" << std::endl;
        return;
    }
    Student *t = first;
    Student *p;
    while (t) {
        p = t;
        t = t->next;
        delete p;
    }
    std::cout << "\n清空成功！\n";
    std::cout << "退出成功！\n";
}

void studentMessage::change() {
    std::string a;
    int b;
    int c;
    std::cout << "\n请输入要查找的学生的学号:";
    std::cin >> a;
    Student *t = first;
    while (t) {
        if (t->ID == a) {
            break;
        }
        t = t->next;
    }
    if (!t) {
        std::cout << "\n未找到要查找学生！" << std::endl;
        return;
    }
    std::cout << "\n请问要修改哪一个成绩？(1.数学，2.英语，3.C++)" << std::endl;
    std::cin >> b;
    std::cout << "\n修改的成绩为：" << std::endl;
    std::cin >> c;
    switch (b) {
        case 1:
            t->score[1] = c;
            break;
        case 2:
            t->score[2] = c;
            break;
        case 3:
            t->score[3] = c;
            break;
    }
}