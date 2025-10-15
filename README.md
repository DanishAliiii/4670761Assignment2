# 4670761Assignment2 – Mobile Application Development Project

This repository contains the final Android application developed for **NIT3213 – Mobile Application Development (Assignment 2)**.  
It demonstrates API integration, dependency injection (Hilt), MVVM architecture, RecyclerView UI design, and unit testing.

---

## 👤 Student Information
| Field | Details |
|--------|----------|
| **Name** | Danish Ali |
| **Student ID** | 4670761 |
| **Campus** | Footscray |
| **Unit Code** | NIT3213 |
| **Assessment** | Assignment 2 – Final Android App |

---

## 🎯 Objective
Create a three-screen Android app (Login → Dashboard → Details) that authenticates a user and fetches data from a REST API using Retrofit.

---

## 🧱 Architecture Overview
The app follows **MVVM (Model–View–ViewModel)** with modern Android practices:

- **Hilt DI** – dependency injection  
- **Retrofit + Gson** – REST API integration  
- **Coroutines + LiveData** – background processing  
- **DataStore** – local persistence  
- **Navigation Component** – screen navigation  
- **RecyclerView** – dashboard listing  

---

## ⚙️ Setup & Build Instructions

### 🧩 Prerequisites
- Android Studio (Giraffe / Hedgehog or newer)  
- JDK 17+  
- Minimum SDK 24 / Target SDK 34  
- Internet connection (for API calls)

### 🧰 Steps
1. Clone this repository:
   ```bash
   git clone https://github.com/DanishAliiii/4670761Assignment2.git
