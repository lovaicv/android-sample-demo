//package com.iebayirli.recyclerviewwithdatabinding
//
//import android.util.Log
//
//open class test(val name: String) {
//    val a = 0
//
//    open fun show() {
//        Log.d("check", "$name")
//    }
//}
//
//class test2(name: String) : test(name) {
//    override fun show() {
//        super.show()
//        Log.d("check", "$a")
//    }
//}
//
//internal open class Human {
//    //Overridden method
//    open fun eat() {
//        println("Human is eating")
//    }
//}
//
//internal class Boy : Human() {
//    //Overriding method
//    override fun eat() {
//        println("Boy is eating")
//    }
//
//    companion object {
//        @JvmStatic
//        fun main(args: Array<String>) {
//            val obj = Boy()
//            //This will call the child class version of eat()
//            obj.eat()
//        }
//    }
//}
//
//abstract class repo {
//    abstract fun sound()
//}
//
//class test3 : repo() {
//    override fun sound() {
//        super.makeSound()
//    }
//}
//
//interface A {
//    fun makeSound()
//}
//
//class B : A{
//    override fun makeSound() {
//        super.makeSound()
//    }
//}
//
////======================================================================
//
//// Abstract class example
//abstract class Animal(val name: String) {
//
//    // Concrete method
//    fun introduction() {
//        println("My name is $name")
//    }
//
//    // Abstract method, to be implemented by subclasses
//    abstract fun makeSound()
//
//
//}
//
//// Subclass of Animal, which must implement makeSound()
//class Dog(name: String) : Animal(name) {
//
//    override fun makeSound() {
//        println("Woof!")
//    }
//}
//
//// Interface example
//interface Shape {
//
//    // Abstract method
//    fun area(): Double
//
//    // Default implementation
//    fun perimeter(): Double {
//        return 0.0
//    }
//}
//
//// Class that implements Shape interface
//class Rectangle(val length: Double, val width: Double) : Shape {
//
//    // Implementation of area() method
//    override fun area(): Double {
//        return length * width
//    }
//
//    // Override of perimeter() method with custom implementation
//    override fun perimeter(): Double {
//        return 2 * (length + width)
//    }
//}
//
//fun main() {
//    val dog = Dog("Fido")
//    dog.introduction() // My name is Fido
//    dog.makeSound() // Woof!
//
//    val rectangle = Rectangle(5.0, 3.0)
//    println("Area of rectangle is ${rectangle.area()}") // Area of rectangle is 15.0
//    println("Perimeter of rectangle is ${rectangle.perimeter()}") // Perimeter of rectangle is 16.0
//}