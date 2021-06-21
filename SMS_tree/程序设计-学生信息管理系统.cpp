#include<iostream>
#include<fstream>
#include<cstring>
#include<cstdlib>

using namespace std;
/*主菜单
1.查询(组合条件查询，模糊查询，统计满足查询条件的人数，按行显示结果) 
2.添加学生信息(学号查重，比对12个学号字符，性别输入正确字符比对) 
3.删除学生信息() 
4. 更改学生信息
5.退出 
*/
//学号	姓名	性别	年龄	籍贯	专业 
class Student {
public:
    char id[20];
    char name[20];
    char sex[8];
    char age[4];
    char pro[20];
    char major[20];
};

//索引结构 
class Index {
public:
    char key[20];//关键字
    int pos;//偏移位置
};

//二叉树
struct tree {
    Index data;
    tree *left, *right;
};


//建树 
void createbitree(Index index, tree *&p) {
    if (!p) {        //根结点为空
        p = new tree;
        p->data = index;
        p->left = NULL;
        p->right = NULL;
    } else if (strcmp(index.key, p->data.key) < 0) {
        //新插入结点学号比根结点学号小
        //新插入结点学号比根结点学号小
        createbitree(index, p->left);
    } else if (strcmp(index.key, p->data.key) > 0) {
        //新插入结点学号比根结点学号小大
        createbitree(index, p->right);
    } else {
        //有相同学号
        cout << "error" << endl;
        exit(0);
    }
}

//建索引
Index createIndex(Student stu, Index elems, int indexcount) {
    memcpy(elems.key, stu.id, 20);//将文本中的学号信息赋值给索引中的关键字
    elems.pos = indexcount * 92;///将信息的偏移位置赋值给索引中的偏移量
    return elems;
}

//插入新索引
Index InsertIndex(Student stu, Index elems) {
    fstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    file.seekg(0, ios::end);  //指针到尾
    memcpy(elems.key, stu.id, 20);
    elems.pos = file.tellg();
    return elems;
}


//删除二叉树结点 
void delIndex(tree *&t, char id[20]) {
    {
        tree *f, *q, *s, *p;
        p = t;
        f = NULL;
        //查找关键字为k的结点，同时将此结点的双亲找出来
        while (p && strcmp(id, p->data.key)) {
            f = p; //f记录父结点
            if (strcmp(id, p->data.key) < 0)
                p = p->left;
            else
                p = p->right;
        }
        if (!p)//找不到待删除结点时返回
            return;
        if (p->left == NULL) //待删除结点的左子树为空
        {
            if (f == NULL)//待删除结点为根结点
                t = p->right;
            else if (f->left == p)//待删除结点是其双亲结点的左节点
                f->left = p->right;
            else                //待删除结点是其双亲结点的右结点
                f->right = p->right;
            delete p;
        } else //待删除结点有左子树
        {
            q = p;
            s = p->left;
            while (s->right) //在待删除结点的左子树中查找最右下结点,即查找待删除结点的中序前驱结点
            {
                q = s;
                s = s->right;
            }
            if (q == p)
                q->left = s->left;
            else
                q->right = s->left;
            // memcpy(p->data.key,s->data.key,20);
            p->data = s->data;
            delete s;
        }
    }
}

//修改偏移值
tree *updateIndex(tree *&t, tree *c, int &pos, char id[20]) {
    if (t == 0) {
        cout << "return 0" << endl;
        return 0;
    }
    if (strcmp(id, t->data.key) < 0 && t->data.pos > pos) {
        c->data.pos = t->data.pos - 92;//偏移值往前移动一个学生信息长度
        updateIndex(t->left, c->left, pos, id);
    }

    if (strcmp(id, t->data.key) > 0 && t->data.pos > pos) {
        c->data.pos = t->data.pos - 92;//偏移值往前移动一个学生信息长度
        updateIndex(t->right, c->right, pos, id);
    }
    return c;
}

//重建树,耗时间空间 
tree *updateIndex() {
    Student stu;
    tree *root;
    root = NULL;
    Index index;
    Index newindex;
    int count = 0;
    int indexcount = 0;
    fstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    file.seekg(0, ios::end);  //指针到尾
    count = file.tellg() / sizeof(stu);
    file.seekg(0);//指针回到头

    for (int i = 0; i < count; i++) {
        file.read((char *) &stu, sizeof(stu));

        newindex = createIndex(stu, index, indexcount);
        indexcount++;
        createbitree(newindex, root);
    }
    return root;
    file.close();
}


void lookMenu() {
    //查询菜单
    cout << "	  请选择功能键1-5" << endl;
    cout << "	  1.查询所有" << endl;
    cout << "	  2.学号查询" << endl;
    cout << "	  3.条件查询" << endl;
    cout << "	  4.模糊查询" << endl;
    cout << "	  5.返回" << endl;
    cout << "    ------请输入选择(1-5)------" << endl;
}

void conditionMenu() {
    //条件查询菜单
    cout << "1.姓名" << endl;
    cout << "2.性别" << endl;
    cout << "3.年龄" << endl;
    cout << "4.籍贯" << endl;
    cout << "5.专业" << endl;
    cout << "" << endl;
    cout << "" << endl;
}

//两条件查询
void twocondition(int one, int two) {
    Student stu;
    int count;
    int flag = 0;
    int i;
    char id[20];
    char name[20];
    char sex[8];
    char age[4];
    char pro[20];
    char major[20];
    fstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    file.seekg(0, ios::end);  //指针到尾

    count = file.tellg() / sizeof(stu);

    file.seekg(0);//指针回到头


    if (one == 2 && two == 5) {

        cout << "请输入性别" << endl;
        cin >> sex;
        cout << "请输入专业" << endl;
        cin >> major;
        cout << "学号" << "\t\t" << "姓名" << "\t" << " " << "性别" << "\t" << "年龄" << "\t" << "籍贯" << "\t\t" << "专业" << "\n";
        cout << "\n";

        for (i = 0; i < count; i++) {
            file.read((char *) &stu, sizeof(stu)); //两条件查询 ，全部符合才输出显示
            if (strcmp(sex, stu.sex) == 0 && strcmp(major, stu.major) == 0) {
                cout << stu.id << "\t";
                cout << stu.name << "\t" << " ";
                cout << stu.sex << "\t";
                cout << stu.age << "\t";
                cout << stu.pro << "\t";
                cout << stu.major << "\t";
                cout << "" << endl;
                flag++;
            }
        }
        cout << "\n";
        cout << "\n";
        cout << "\n";
        cout << "\t\t\t\t\t\t记录：" << flag << " 条" << endl;
        file.close();
    } else if (one == 2 && two == 4) {

        cout << "请输入性别" << endl;
        cin >> sex;
        cout << "请输入籍贯" << endl;
        cin >> pro;
        cout << "学号" << "\t\t" << "姓名" << "\t" << " " << "性别" << "\t" << "年龄" << "\t" << "籍贯" << "\t\t" << "专业" << "\n";
        cout << "\n";

        for (i = 0; i < count; i++) {
            file.read((char *) &stu, sizeof(stu));
            if (strcmp(sex, stu.sex) == 0 && strcmp(major, stu.major) == 0) {
                cout << stu.id << "\t";
                cout << stu.name << "\t" << " ";
                cout << stu.sex << "\t";
                cout << stu.age << "\t";
                cout << stu.pro << "\t";
                cout << stu.major << "\t";
                cout << "" << endl;
                flag++;
            }

        }
        cout << "\n";
        cout << "\n";
        cout << "\n";
        cout << "\t\t\t\t\t\t记录：" << flag << " 条" << endl;
        file.close();
    } else {
        cout << "该功能尚未开放" << endl;
        cout << "" << endl;
    }


}

//查找学号 
void search(char id[20], tree *&t, int &pos) {
//递归查找 
    if (t == 0) {
        return;    //没找到 返回退出递归
    }
    if (strcmp(id, t->data.key) < 0) {
        search(id, t->left, pos);
    } else if (strcmp(id, t->data.key) > 0) {
        search(id, t->right, pos);
    } else {
        pos = t->data.pos;//返回偏移值用以快速定位
        return;
    }


}

//模糊查询
void find() {
    char text[20];
    system("cls");
    cout << "请输入查找内容" << endl;
    cin >> text;
    int length = 0;
    int i;
    int flag = 0;
    Student stu;
    for (i = 0; i <= 20; i++) {
        if (text[i]) {
            length++;
        } else {
            break;
        }
    }
    bool isNum = false;
    int carrybit;//指针移动单位
    for (i = 0; i < length; i++) {
        if (text[i] >= '0' && text[i] <= '9') {//判断是否为数字
            carrybit = 1;
            isNum = true;
        } else {
            carrybit = 2;
            isNum = false;
            break;
        }
    }
    bool isFind = false;
    char temp[length];
    cout << "\n";
    cout << "学号" << "\t\t" << "姓名" << "\t" << " " << "性别" << "\t" << "年龄" << "\t" << "籍贯" << "\t\t" << "专业" << "\n";
    int count;
    cout << "\n";
    ifstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    file.seekg(0, ios::end);  //指针到尾
    count = file.tellg() / 92;//所有学生记录数
    for (i = 0; i < count; i++) {    //循环读取所有文本内容
        isFind = false;
        //j=0时为查询所有，j=20为跳过学号
        for (int j = 20; j < 92; j = j + carrybit) {
            file.seekg(i * 92);    //从文本头开始移动一段学生信息长度
            file.seekg(j, ios::cur);//当前位置后移j个单位  当输入内容为数字 carrybit=1
            //是汉字则carrybit=2
            file.read((char *) &temp, length);//读取文本中输入内容的长度的信息
            if (temp[0] == text[0]) {    //比较输入内容与读取到的内容是否相同
                for (int k = 0; k < length; k++) {
                    if (temp[k] == text[k]) {
                        isFind = true;
                    } else {
                        isFind = false;
                        break;
                    }
                }
                if (isFind == true) {
                    break;
                }
            }
        }
        //回到头
        file.seekg(i * 92, ios::beg);
        if (isFind == true) {
            file.read((char *) &stu, 92);
            cout << stu.id << "\t";
            cout << stu.name << "\t" << " ";
            cout << stu.sex << "\t";
            cout << stu.age << "\t";
            cout << stu.pro << "\t";
            cout << stu.major << "";
            cout << "" << endl;
            flag++;//记录数
        }
    }
    cout << "\n";
    cout << "\n";
    cout << "\n";
    cout << "\t\t\t\t\t\t记录：" << flag << " 条" << endl;
    file.close();
}


//遍历树
void print(tree *&t) {
    if (t) {

        print(t->left);
//	cout<<t->data<<" ";
        cout << t->data.key << endl;
        cout << t->data.pos << endl;
        print(t->right);
    }
}

int Menu() {
    //菜单
    int n;//switch判断
    cout << "***************************************************" << endl;
    cout << "**********				***********" << endl;
    cout << "**********	学生信息管理系统	***********" << endl;
    cout << "**********				***********" << endl;
    cout << "***************************************************" << endl;
    cout << "" << endl;
    cout << "	  请选择功能键1-5" << endl;
    cout << "	  1.查询学生信息" << endl;
    cout << "	  2.添加学生信息" << endl;
    cout << "	  3.删除学生信息" << endl;
    cout << "	  4.更改学生信息" << endl;
    cout << "	  5.退出" << endl;
    cout << "" << endl;
    cout << "    ------请输入选择(1-5)------" << endl;
    cin >> n;
    if (!cin >> n) {
        cout << "输入错误" << endl; //输入类型错误退出
        cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
        cin.sync();//清楚cin缓存区的数据。
    } else {
        return n;
    }
}

//查询学生信息
int look(tree *&t) {
    Student stu;
    int choose;
    int count = 0;
    while (true) {

        lookMenu();
        cin >> choose;
        if (!cin >> choose) {
            cout << "输入错误" << endl; //出错
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        }
        switch (choose) {
            case 1://查询 所有
            {
                fstream file("E:/mycode/student.txt");
                if (!file) {
                    cout << "打开文件失败" << endl;
                    exit(0);
                }
                system("cls");
                cout << "\n";
                cout << "学号" << "\t\t" << "姓名" << "\t" << " " << "性别" << "\t" << "年龄" << "\t" << "籍贯" << "\t\t" << "专业"
                     << "\n";
                cout << "\n";
                file.seekg(0, ios::end);  //指针到尾
                count = file.tellg() / sizeof(stu);    //计算长度
                file.seekg(0);//指针回到头
                for (int i = 0; i < count; i++) {
                    file.read((char *) &stu, sizeof(stu));
                    cout << stu.id << "\t";
                    cout << stu.name << "\t" << " ";
                    cout << stu.sex << "\t";
                    cout << stu.age << "\t";
                    cout << stu.pro << "\t";
                    cout << stu.major << "";
                    cout << "" << endl;
                }
                cout << "\n";
                cout << "\n";
                cout << "\n";
                cout << "\t\t\t\t\t\t记录：" << count << " 条" << endl;
                file.close();
                cout << "" << endl;
                break;
            }
            case 2: {
                tree *p;
                fstream file("E:/mycode/student.txt");
                if (!file) {
                    cout << "打开文件失败" << endl;
                    exit(0);
                }
                int pos = -1;
                cout << "请输入学生学号" << endl;
                cin >> stu.id;
                search(stu.id, t, pos);
                file.seekg(pos);
                if (pos >= 0) {
                    system("cls");
                    cout << "学号" << "\t\t" << "姓名" << "\t" << " " << "性别" << "\t" << "年龄" << "\t" << "籍贯" << "\t\t"
                         << "专业" << "\n";
                    cout << "\n";
                    file.read((char *) &stu, sizeof(stu));
                    cout << stu.id << "\t";
                    cout << stu.name << "\t" << " ";
                    cout << stu.sex << "\t";
                    cout << stu.age << "\t";
                    cout << stu.pro << "\t";
                    cout << stu.major << "\t";
                    cout << "" << endl;
                    cout << "\n";
                    cout << "\n";
                    cout << "\n";
                } else {
                    system("cls");
                    cout << "找不到该学生信息" << endl;
                    cout << "" << endl;
                }
                file.close();
            }
                break;
            case 3://模糊查询
            {
                int i;
                fstream file("E:/mycode/student.txt");
                if (!file) {
                    cout << "打开文件失败" << endl;
                    exit(0);
                }
                system("cls");
                conditionMenu();//条件菜单

                int flag = 0;
                char name[20];
                char sex[8];
                char age[4];
                char pro[20];
                char major[20];
                int con;
                int onecon;
                int twocon1;
                int twocon2;
                system("cls");
                cout << "请选择查询条件个数" << endl;
                cin >> con;
                if (con == 2) {
                    conditionMenu();
                    cout << "请选择任意两个条件查询" << endl;
                    cout << "请输入第一个条件编号" << endl;
                    cin >> twocon1;
                    cout << "请输入第二个条件编号" << endl;
                    cin >> twocon2;
                    twocondition(twocon1, twocon2);
                } else if (con == 1) {
                    cout << "模糊查询可满足除学号外的单条件查询，请重新选择" << endl;
                    cout << "" << endl;
                    cout << "" << endl;
                } else {
                    cout << "该功能尚未开放" << endl;
                    cout << "" << endl;
                    cout << "" << endl;
                }


            }
                break;
            case 4:
                find();
                break;
            case 5://返回
            {
                cout << "是否返回上级菜单?(y/n)" << endl;
                char x;
                cin >> x;
                if (x == 'y' || x == 'Y') {
                    system("cls");
                    return 0;
                } else if (x == 'n' || x == 'N') {
                    system("cls");
                    break;
                } else {
                    system("cls");
                    cout << "输入错误" << endl;

                }
                default:
                    cout << "无此功能" << endl;
                break;
            }
        }
    }
}


//添加学生信息
void add(tree *&t) {
    Student stu;
    Index elems;
    Index newindex;
    int i;
    int pos = -1;
    int flag;
    int length;
    fstream file("E:/mycode/student.txt", ios::app);
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    memset(&stu, '\0', sizeof(stu));//请乱码缓存
    cout << "请输入学生学号" << endl;
    do {
        flag = 0;
        length = 0;
        cin >> stu.id;
        for (i = 0; i < sizeof(stu.id); i++) {
            if (stu.id[i]) {
                length++;//记录输入内容的长度
            } else {
                break;
            }
        }
        for (i = 0; i < length; i++) {//判断是否为数字
            if ((stu.id[i] >= 'a' && stu.id[i] <= 'z') || (stu.id[i] >= 'A' && stu.id[i] <= 'Z')) {
                flag++;
            }
        }
        //判断输入是否合法
        if (length != 12 || flag > 0) {
            cout << "请输入长度为12的数字学号" << endl;
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清除cin缓存区的数据。
        }
    } while (flag > 0 || length != 12 || pos > 0);

    search(stu.id, t, pos);
    if (pos >= 0) {
        cout << "该学生信息已存在" << endl;
        return;
    }
    cout << "请输入学生姓名" << endl;
    do {
        cin >> stu.name;
        length = 0;
        for (i = 0; i < 1000; i++) {
            if (stu.name[i]) {
                length++;
            } else {
                break;
            }
        }
        if (length > 20) {
            cout << "名字过长,请重新输入" << endl;
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        }
    } while (length > 20);

    cout << "请输入学生年龄" << endl;

    //年龄
    do {
        flag = 0;
        length = 0;
        cin >> stu.age;
        for (i = 0; i < sizeof(stu.age); i++) {
            if (stu.age[i]) {
                length++;
            } else {
                break;
            }
        }


        for (i = 0; i < length; i++) {
            if (stu.age[i] > '9' || stu.age[i] < '0') {
                flag++;
            }
        }
        //判定年龄在合适的区间
        if (strcmp(stu.age, "16") < 0 || strcmp(stu.age, "60") > 0) {
            flag++;
        }
        if (flag > 0 || length != 2) {
            cout << "年龄输入错误,请重新输入实际年龄" << endl;
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        }
    } while (flag > 0 || length != 2);


    cout << "请输入学生性别" << endl;
    do {
        cin >> stu.sex;
        if (!strcmp(stu.sex, "男") || !strcmp(stu.sex, "女")) {//判断性别输入是否合法
            flag = 1;
        } else {
            cout << "性别输入错误,请重新输入" << endl;
            flag = 0;
        }
        cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
        cin.sync();//清楚cin缓存区的数据。
    } while (flag == 0);


    cout << "请输入学生籍贯" << endl;
    do {
        cin >> stu.pro;
        length = 0;
        for (i = 0; i < 1000; i++) {
            if (stu.pro[i]) {
                length++;
            } else {
                break;
            }
        }
        if (length > 20) {
            cout << "籍贯输入错误,请重新输入" << endl;
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        }
    } while (length > 20);

    cout << "请输入学生专业" << endl;
    do {
        cin >> stu.major;
        length = 0;
        for (i = 0; i < 1000; i++) {
            if (stu.major[i]) {
                length++;
            } else {
                break;
            }
        }
        if (length > 20) {
            cout << "专业长度不得超过20,请重新输入" << endl;
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        }
    } while (length > 20);


    file.write((char *) &stu, sizeof(stu));
    newindex = InsertIndex(stu, elems);
    createbitree(newindex, t);

    file.close();
    cout << "添加成功" << endl;

}

void del(tree *&t) {
    Student stu;
    tree *c;
    ifstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    //将文本内容切割成前半部分-被删除部分-后半部分
    //将前半部分-后半部分 重新写入文本
    cout << "请输入要删除的学号" << endl;
    cin >> stu.id;
    int pos = -1;//初始化偏移值
    search(stu.id, t, pos);//查询学号是否存在 并改变偏移值
    char front[pos];//定义储存前半部分信息的容器
    if (pos >= 0) {//学号存在
        file.seekg(0, ios::end); //指针到尾
        unsigned int backlength = file.tellg() - pos - sizeof(stu);//计算后半部分文本长度
        file.seekg(0, ios::beg);//指针回到头
        file.read((char *) &front, pos);
        char back[backlength];//定义储存后半部分信息的容器
        file.seekg(92, ios::cur);//移动被删除
        file.read((char *) &back, backlength);
        file.close();
        // 添加前半部分信息
        ofstream writefile("E:/mycode/student.txt", ios::ate);
        writefile.seekp(0);//指针回到头
        writefile.write((char *) &front, pos);
        writefile.close();
        //文本末追加后半部分信息
        ofstream bwritefile("E:/mycode/student.txt", ios::app);
        bwritefile.write((char *) &back, backlength);
        bwritefile.close();

        updateIndex(t, c, pos, stu.id);//修改偏移值
        delIndex(t, stu.id);//删除结点

        cout << "删除成功" << endl;
    } else {
        //system("cls");
        cout << "找不到该学生信息" << endl;
    }
}

void update(tree *&t) {
    int i;
    int flag;
    int length;
    Student stu;
    int pos = -1;
    memset(&stu, '\0', sizeof(stu));//不清缓存更改会部分乱码
    fstream file("E:/mycode/student.txt");
    if (!file) {
        cout << "打开文件失败" << endl;
        exit(0);
    }
    cout << "请输入学生的学号" << endl;
    cin >> stu.id;
    search(stu.id, t, pos);//找学号
    file.seekp(pos); //定位
    if (pos >= 0) {

        cout << "请输入要修改的学生姓名" << endl;
        do {
            cin >> stu.name;
            length = 0;
            for (i = 0; i < 1000; i++) {
                if (stu.name[i]) {
                    length++;
                } else {
                    break;
                }
            }
            if (length > 20) {
                cout << "名字过长,请重新输入" << endl;
                cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
                cin.sync();//清楚cin缓存区的数据。
            }
        } while (length > 20);


        cout << "请输入要修改的学生年龄" << endl;
        do {
            flag = 0;
            length = 0;
            cin >> stu.age;
            for (i = 0; i < sizeof(stu.age); i++) {
                if (stu.age[i]) {
                    length++;//计算输入内容的长度 用以判断是否超过储存年龄的字符数组长度
                } else {
                    break;
                }
            }
            for (i = 0; i < length; i++) {//判断年龄输入是否为数字
                if (stu.age[i] > '9' || stu.age[i] < '0') {
                    flag++;//标识符 判断输入内容是否存在数字
                }
            }

            if (strcmp(stu.age, "16") < 0 || strcmp(stu.age, "60") > 0) {
                flag++;//标识符  判定年龄在是否合适的区间
            }
            if (flag > 0 || length != 2) {
                cout << "年龄输入错误,请重新输入实际年龄" << endl;
                cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
                cin.sync();//清除cin缓存区的数据。
            }
        } while (flag > 0 || length != 2);


        cout << "请输入要修改的学生性别" << endl;
        do {
            cin >> stu.sex;
            if (!strcmp(stu.sex, "男") || !strcmp(stu.sex, "女")) {//判断性别输入是否合法
                flag = 1;
            } else {
                cout << "性别输入错误,请重新输入" << endl;
                flag = 0;
            }
            cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
            cin.sync();//清楚cin缓存区的数据。
        } while (flag == 0);

        cout << "请输入要修改的学生籍贯" << endl;
        do {
            cin >> stu.pro;
            length = 0;
            for (i = 0; i < 1000; i++) {
                if (stu.pro[i]) {
                    length++;
                } else {
                    break;
                }
            }
            if (length > 20) {
                cout << "籍贯输入错误,请重新输入" << endl;
                cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
                cin.sync();//清楚cin缓存区的数据。
            }
        } while (length > 20);
        cout << "请输入要修改的学生专业" << endl;
        do {
            cin >> stu.major;
            length = 0;
            for (i = 0; i < 1000; i++) {


                if (stu.major[i]) {
                    length++;
                } else {
                    break;
                }
            }
            if (length > 20) {
                cout << "专业长度不得超过20,请重新输入" << endl;
                cin.clear();//清除错误标记，重新打开输入流，但是输入流中依旧保留着之前的不匹配的类型
                cin.sync();//清楚cin缓存区的数据。
            }
        } while (length > 20);
        file.write((char *) &stu, sizeof(stu));
    } else {
        system("cls");
        cout << "找不到该学生信息" << endl;
        cout << "" << endl;
    }
    file.close();
}


int main() {
    tree *root;
    root = updateIndex(); //建立索引
    while (true) {
        switch (Menu()) {
            case 1:
                system("cls");//清屏
                look(root); //进入查询学生信息选择界面
                break;
            case 2:
                system("cls");
                add(root);//添加学生信息
                break;
            case 3:
                system("cls");
                del(root);// 删除学生信息
                break;
            case 4:
                system("cls");
                update(root);//更改学生信息
                break;
            case 5://退出系统
            {
                cout << "是否确认退出学生信息管理系统?(y/n)" << endl;
                char x;
                cin >> x;
                if (x == 'y' || x == 'Y') {
                    return 0;
                } else if (x == 'n' || x == 'N') {
                    system("cls");
                    break;
                } else {
                    cout << "输入错误" << endl;
                    break;
                }
            }
            default:
                cout << "无此功能" << endl;
                break;
        }
    }
    return 0;
}

