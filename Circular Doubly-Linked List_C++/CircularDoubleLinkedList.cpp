#include "CircularDoubleLinkedList.h"
#include <cstddef>
#include <iostream>
#include "Node.h"
#include "Node.cpp"

using namespace std;

//Constructor
template <class T>
CircularDoubleLinkedList<T>::CircularDoubleLinkedList()
{
    this->head = NULL;
}

template <class T>

//Destructor
CircularDoubleLinkedList<T>::~CircularDoubleLinkedList()
{  
    if (this->head != NULL)
    {
        Node<T> *nodePtr = this->head->prev;
        while(nodePtr != this->head)
        {
            nodePtr = nodePtr->prev;
            delete nodePtr->next;
        }

        delete nodePtr;
        nodePtr = NULL;
    }
}


//returns the head node
template <class T>
Node<T>* CircularDoubleLinkedList<T>::getHeadNode()
{
    return this->head;    
}


//Returns the size of linked list
template <class T>
int CircularDoubleLinkedList<T>::getSize() const
{
    if (this->head == NULL)
    return 0;

    Node<T> *nodePtr = this->head;
    int size = 0;
    while(this->head != nodePtr->next)
    {
        nodePtr = nodePtr->next;
        size++;
    }
    size++;

    return size;
}

//creates a new node with passed in value
template <class T>
void CircularDoubleLinkedList<T>::operator+=(const T val)
{
    Node<T> *newNode;
    newNode = new Node<T>(val);
    Node<T> *nodePtr;
    nodePtr = this->head;
    if (this->head == NULL)
    {
        this->head = newNode;
        newNode->prev = this->head;
        newNode->next = this->head;
    }
    else
    {
        newNode->next = this->head;
        newNode->prev = this->head->prev;
        this->head->prev->next = newNode;
        this->head->prev = newNode;
    }
}

//Adds passed in linked list to the current one
template <class T>
void CircularDoubleLinkedList<T>::operator+=(const CircularDoubleLinkedList<T>& list)
{
    if (list.head != NULL)
    {
        Node<T> *nodePtr;
        nodePtr = list.head;
        for (int i = 1; i <= list.getSize();i++)
        {
            this->operator+=(*(nodePtr->value));
            nodePtr = nodePtr->next;
        }
    }    
}

//Returns value of node of given index
template <class T>
T CircularDoubleLinkedList<T>::operator[](const int index)
{
    if (this->head == NULL || this->getSize() <= 0 || index < 0)
    return -1;

    Node<T> *nodePtr;
    nodePtr = this->head;

    for (int i = 0; i < index;i++)
    {
        nodePtr = nodePtr->next;
    }

    return *(nodePtr->value);

}

//Inserts new node at given index
template <class T>
bool CircularDoubleLinkedList<T>::insertNodeAt(const int index, const T val)
{
    if (index >= 0 && index <=  this->getSize())
    {
        if (this->head == NULL)
        this->operator+=(val);
        else
        {
            Node<T> *nodePtr;
            nodePtr = this->head;
            Node<T> *newNode;
            newNode = new Node<T>(val);
            for (int i = 0; i < index;i++)
            {
                nodePtr = nodePtr->next;
            }       
            newNode->next = nodePtr;
            newNode->prev = nodePtr->prev;
            nodePtr->prev->next = newNode;
            nodePtr->prev = newNode;
            if (index == 0)
            {
                this->head = newNode;
            }
        }
        return true;
    }
    



    return false;
}

//Node that has value passed in as a paramerter is deleted
template <class T>
bool CircularDoubleLinkedList<T>::deleteNode(T val)
{
    bool ans = false;

    Node<T> *nodePtr;
    nodePtr = this->head;

    if (this->getSize() == 1)
    {
        if (*(this->head->value) == val)
        {
            delete this->head;
            this->head = NULL;
            ans = true;
        }
    }
    else
    {
        for (int i = 0; i < this->getSize();i++)
        {
            if (*(nodePtr->value) == val)
            {
                ans = true;
                i = this->getSize();

                nodePtr->prev->next = nodePtr->next;
                nodePtr->next->prev = nodePtr->prev;

                if (nodePtr == this->head)
                {
                    this->head = nodePtr->next;
                }

                delete nodePtr;
                nodePtr = NULL;
            }
            else
            nodePtr = nodePtr->next;
        }
    }

    return  ans;

}

//Compare lists to see if equal
template <class T>
bool CircularDoubleLinkedList<T>::operator==(const CircularDoubleLinkedList<T>& list)
{
    bool ans = true;

    if (this->getSize() != list.getSize())
    ans = false;
    else
    {
        Node<T> *nodePtr;
        nodePtr = this->head;
        Node<T> *listNode;
        listNode = list.head;
        for (int i = 0; i <this->getSize();i++)
        {
            if (*(nodePtr->value) != *(listNode->value))
            {
                i = this->getSize();
                ans =false;
            }
            else
            {
                nodePtr = nodePtr->next;
                listNode = listNode->next;
            }
        }
    }

    return ans;
}


//Provides an output to the list
template <class T>
ostream& operator<<(ostream& output,const CircularDoubleLinkedList<T>& list)
{
    Node<T> *nodePtr;
    nodePtr = list.head;
    if (nodePtr != NULL)
    {
        for (int i = 0; i < (list.getSize() - 1);i++)
        {
            output << *(nodePtr->value) << "[" << *(nodePtr->prev->value) << "," << *(nodePtr->next->value) << "] -> ";
            nodePtr = nodePtr->next;
            
        }
        output << *(nodePtr->value) << "[" << *(nodePtr->prev->value) << "," << *(nodePtr->next->value) << "]";
    }
    else
    {
        output <<"";
    }

    

    return output;
}