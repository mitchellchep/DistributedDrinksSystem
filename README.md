# Distributed Drinks Ordering System

A Java RMI-based system where customers place drink orders to different branches and admins can view business reports.

---

## 🧭 Step-by-Step Setup Guide


### 🖥️ On the HQ Server (Linux)

### 🛠️ Step 0: Get Your IP Address (Server & Client)

Before doing anything else run
``` bash 
ipconfig 
```

#### 1. Navigate to project root folder
```bash
cd DistributedDrinksSystem
```

#### 2. Compile all Java files
```bash
javac -d out shared/*.java server/*.java
```

#### 3. Start the RMI Registry
```bash
cd out
rmiregistry 1099 &
```

#### 4. Run the HQ Server
```bash
java server.HQServer
```

✅ Output: `HQ Server ready at port 1099`

---

### 💻 On Client PC (e.g., Windows)

Make sure the project contains only the following folders:
- `shared/`
- `client/`

#### 1. Open Command Prompt / PowerShell and navigate to the project folder
```powershell
cd DistributedDrinksSystem
```

#### 2. Compile the client code
```powershell
javac -d out shared/*.java client/*.java
```

#### 3. Run the Client Application
```powershell
java -cp out client.CustomerClient <HQ_SERVER_IP>
```

Replace `<HQ_SERVER_IP>` with the actual IP address of the Linux server (e.g., `192.168.100.33`).

---

### 👑 On Admin PC

Ensure the project contains:
- `shared/`
- `admin/`

#### 1. Compile the admin interface
```powershell
javac -d out shared/*.java admin/*.java
```

#### 2. Run the Admin Interface
```powershell
java -cp out admin.AdminClient <HQ_SERVER_IP>
```

---

## 📌 Important Notes

- The `shared/` package must be **identical** on all machines.
- All devices must be on the **same local network**.
- Recompile and redeploy `shared/*.java` if any class changes (like `Order` or `Drink`).
- Delete all `.class` files and recompile if you see `serialVersionUID` errors.

---

## 🛠️ Troubleshooting

| Issue                      | Solution                                                                 |
|---------------------------|--------------------------------------------------------------------------|
| Connection refused         | Ensure server is running and IP is correct                              |
| InvalidClassException      | Make sure all machines use identical `shared/*.class` files              |
| java.rmi.NotBoundException | Ensure `rmiregistry` is running and `OrderService` is registered        |
| Network errors             | Ensure devices are connected to the same WiFi and TCP port 1099 is open |

---

## 🔐 Roles

| Role     | Capabilities                                |
|----------|---------------------------------------------|
| Customer | Place drink orders to any branch            |
| Admin    | View sales reports, reset the system        |

---

## 🧪 Sample Order Flow

1. Customer opens the UI and connects to HQ using IP `192.168.100.33`.
2. Fills in their name, selects a branch, drink, and quantity.
3. Clicks **Place Order**.
4. ✅ Order is placed and confirmation shown.
5. They can place another order as per their preference.

---


Happy coding! 🚀