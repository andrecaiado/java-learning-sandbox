# 🎯 Java Sandbox - Agentic Coding Bootcamp

This repository is a continuous, practical training environment designed to build and solidify a dependable **Mid-Level** engineering profile in modern Java (LTS 25).

The project utilizes the concept of **Agentic Coding** via **OpenCode**, which acts as an encouraging yet strict senior peer. The AI mentor evaluates code, designs practical challenges, and provides real-time architectural code reviews without delivering copy-paste solutions.

---

## 🧠 How the Ecosystem Works

The bootcamp relies on a unified, long-term codebase architecture where the AI manages state and progression using three specific local configuration files:
*   **`agents.md`**: Outlines the pragmatic mentor profile, enforces technical boundaries, and dictates pedagogical rules.
*   **`progress.md`**: Your interactive, open-ended roadmap organized into technical milestones.
*   **`.opencode/session.json`**: The agent's continuous memory card tracking your engineering strengths, struggles, and technical debt.

---

## 🛠️ Automated Operations (Slash Commands)

The workspace is configured with native OpenCode shortcuts to streamline your daily engineering habits straight from the chat interface:

*   **`/start-training`**: Switches to `main`, pulls the latest infrastructure templates, spins up an isolated timestamped branch (`training-YYYYMMDD-HHMMSS`), and alerts the mentor to begin the technical placement evaluation.
*   **`/resume-training`**: Restores the mentor's context by analyzing your active codebase, `progress.md` checkboxes, and `session.json` history metrics if you close and reopen the app mid-exercise.
*   **`/hint`**: Requests a tier-appropriate conceptual clue or architectural guidance when stuck on an exercise, preventing code spoiling.
*   **`/submit-exercise`**: Compiles code, runs Maven unit tests (`mvn test`), and auto-commits your successful daily implementation using clean *Conventional Commits* standards.

---

## 🔄 Daily Git Workflow & Repository Guidelines

To maintain a clean, professional environment that can be securely "reset" or reviewed at any time, we enforce strict Git patterns:

1.  **`main` is Sacred**: The primary branch contains only the clean, empty infrastructure templates (`pom.xml`, configurations, and this README). Never write feature code directly on `main`.
2.  **Total Isolation**: Every single learning run takes place inside its own dedicated branch spawned by `/start-training`.
3.  **No Merges to Main**: Never perform a merge or rebase from a training branch back into `main`. If you wish to restart the course in the future, simply switch back to `main` and execute the start command again.
4.  **Evolving the Teacher**: To optimize the mentor's strictness or expand your technical milestone pool, switch to `main`, patch `agents.md` or `progress.md`, commit your infrastructure updates on `main`, and then merge `main` directly into your active training branch.

---

## 🚀 Getting Started

1. Ensure you have **Java 25 (Eclipse Temurin)** and **Maven** correctly installed and configured globally on your system.
2. Open this root directory inside **OpenCode**.
3. In the OpenCode chat window, execute:
   ```bash
   /start-training
   ```
4. Respond to your mentor's automated message with: *"I'm ready. Please initiate the 5-question interview."*

---

## 💡 Configuration Updates Mid-Training

If you need to update the global infrastructure or behavior of your mentor mid-training, switch to `main`, apply the updates, commit, and rebase your training branch.

⚠️ **CRITICAL RULES:**
*   **NEVER commit your training progress or evaluation state to `main`.**
*   **ONLY allow these configuration files to be committed to `main`:**
   1.  `opencode.json` (Shortcut menus)
   2.  `agents.md` (Mentor rules)
   3.  `progress.md` (Syllabus checklist)
   4.  `README.md` (Documentation)
   5.  `pom.xml` (Dependencies)
*   **ALWAYS keep these files isolated inside your training branches:**
   *   `.opencode/session.json` (Your personal progress state)
   *   `src/main/` and `src/test/` (All your daily exercise code)
