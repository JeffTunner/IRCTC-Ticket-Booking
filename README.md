<div align="center">

# 🎟️ <span style="color:#00BFFF;">Java Ticket Booking System (CLI App)</span>

A **Command-Line Interface (CLI)** based **Train Ticket Booking System** built in **Java**, powered by **Gradle**.  
This project simulates a real-world booking experience — allowing users to **search trains, view seat availability, book seats, view bookings, and cancel tickets**, all stored and managed through **JSON-based persistence**.

</div>

---

## 🚀 Features

<ul style="list-style-type: '✅ '; margin-left: 10px;">
  <li><b>User Authentication</b> — Secure signup/login with BCrypt password hashing and JSON-based persistence.</li>
  <li><b>Train Management</b> — View train details, stations, timings, and real-time seat layouts.</li>
  <li><b>Ticket Booking</b> — Book available seats by specifying row and seat number; updates user and train data automatically.</li>
  <li><b>Booking Management</b> — Fetch all booked tickets, cancel with Ticket ID, and auto-update data.</li>
  <li><b>Data Persistence</b> — Local JSON storage with instant updates after each operation.</li>
</ul>

---

## 🧱 Project Structure

```plaintext
ticket-booking-system/
├── src/
│   ├── main/
│   │   ├── java/ticket/booking/
│   │   │   ├── entities/
│   │   │   │   ├── User.java
│   │   │   │   ├── Train.java
│   │   │   │   └── Ticket.java
│   │   │   ├── services/
│   │   │   │   ├── UserService.java
│   │   │   │   ├── TrainService.java
│   │   │   │   └── BookingService.java
│   │   │   ├── utils/
│   │   │   │   └── FileUtils.java
│   │   │   └── App.java
│   └── resources/
│       ├── users.json
│       └── trains.json
├── build.gradle
└── README.md
```

---

## ⚙️ Tech Stack

<table> <tr><th>Category</th><th>Technology</th></tr> <tr><td>Language</td><td>Java (JDK 17+)</td></tr> <tr><td>Build Tool</td><td>Gradle</td></tr> <tr><td>JSON Processing</td><td>Jackson Databind</td></tr> <tr><td>Password Hashing</td><td>BCrypt</td></tr> <tr><td>Data Storage</td><td>Local JSON Files</td></tr> <tr><td>Interface</td><td>Command-Line (CLI)</td></tr> </table>

---

## 🧩 Installation & Setup

```code
# 1️⃣ Clone the repository
git clone https://github.com/yourusername/ticket-booking-system.git
cd ticket-booking-system

# 2️⃣ Build the project
gradle build

# 3️⃣ Run the application
gradle run
```

---

## 💻 How It Works

<ol>
  <li>User registers or logs in</li>
  <li>View available trains and their seat layout</li>
  <li>Choose a train → select seat → confirm booking</li>
  <li>Ticket details are stored under your account and shown when you fetch bookings</li>
  <li>Cancel any booking using its Ticket ID</li>
</ol>

---

## 📂 Example Data Format

trains.json
```code
[
  {
    "train_id": "ghijk",
    "train_no": "223055",
    "seats": [[0,1,0,0,0,0],[0,0,0,0,0,0]],
    "stations": ["sultanpur", "lucknow", "ghaziabad"],
    "station_times": {
      "sultanpur": "20:30:00",
      "lucknow": "21:10:00",
      "ghaziabad": "22:40:00"
    }
  }
]
```

users.json
```code
[
  {
    "name": "jeff",
    "user_id": "d8407fe5-5fb8-4843-9252-72d46223",
    "hashed_password": "$2a$10$...",
    "tickets_booked": [
      {
        "ticket_id": "f20d2c91-...",
        "train": "ghijk",
        "row": 2,
        "seat": 4
      }
    ]
  }
]
```

---

## 🧠 Learning Highlights

<ul>
  <li>Clean Object-Oriented Design (OOP) principles</li>
  <li>File-based JSON persistence using Jackson</li>
  <li>Gradle build automation</li>
  <li>Error handling and validation</li>
  <li>Modular service-entity architecture</li>
</ul>

---

## 🏆 Future Improvements

<ul>
  <li>🔐 Add Admin Mode for managing trains</li>
  <li>📅 Include Date of Travel and dynamic fare calculation</li>
  <li>🖥️ Build a JavaFX GUI interface</li>
  <li>🗄️ Integrate with a database like MySQL or MongoDB</li>
</ul>

---

## 👨‍💻 Author

<div align="center">

<span style="font-size: 1.2em;">Abhinav Kaushik</span>
🎓 Mechanical Engineering Student | 💻 Java & Web Developer | ⚙️ Problem Solver

📧 [abhinavkaushik05@gmail.com]
🌐 https://github.com/JeffTunner

</div>

---

## ⭐ Contribute

💡 Contributions, issues, and feature requests are welcome!
Feel free to open a pull request or fork the repo to build your version.

## 🛡️ License

This project is open-source under the MIT License.
See the LICENSE
 file for details.

<div align="center">

⭐ If you found this project interesting, consider giving it a star on GitHub! ⭐

</div>
