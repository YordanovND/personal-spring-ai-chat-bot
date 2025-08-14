(() => {
  const $ = sel => document.querySelector(sel);
  const log = $("#log");
  const form = $("#form");
  const input = $("#msg");
  const sendBtn = $("#send");
  const scopeSel = $("#scope");
  const userInp = $("#user");
  const sessInp = $("#session");
  const clearBtn = $("#clearChat");
  const newSessionBtn = $("#newSession");

  // init state (persist user & session)
  const savedUser = localStorage.getItem("chat.user") || "me";
  const savedSess = localStorage.getItem("chat.session") || genId();
  userInp.value = savedUser;
  sessInp.value = savedSess;
  scopeSel.value = localStorage.getItem("chat.scope") || "auto";

  function genId() {
    return Math.random().toString(36).slice(2, 8) + "-" + Date.now().toString(36).slice(-4);
  }

  function add(role, text) {
    const row = document.createElement("div");
    row.className = "row";
    const bubble = document.createElement("div");
    bubble.className = `msg ${role}`;
    bubble.textContent = text;
    const time = document.createElement("span");
    time.className = "time";
    time.textContent = new Date().toLocaleTimeString();
    bubble.appendChild(time);
    row.appendChild(bubble);
    log.appendChild(row);
    log.scrollTop = log.scrollHeight;
  }

  function setLoading(on) {
    form.classList.toggle("loading", on);
    input.disabled = on; sendBtn.disabled = on;
  }

  async function send(message) {
    if (!message.trim()) return;

    // persist current selections
    localStorage.setItem("chat.user", userInp.value.trim() || "me");
    localStorage.setItem("chat.session", sessInp.value.trim() || genId());
    localStorage.setItem("chat.scope", scopeSel.value);

    const payload = {
      message,
      user: userInp.value || "me",
      session: sessInp.value || "default",
      scope: scopeSel.value || "auto"
    };

    add("me", message);
    input.value = "";
    setLoading(true);

    try {
      const res = await fetch("/api/ai/chat", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });

      const text = await res.text();
      let data;
      try { data = JSON.parse(text); } catch { data = { reply: text }; }

      add("bot", data.reply ?? "(no content)");
    } catch (err) {
      add("bot", `⚠️ Error: ${err.message}`);
    } finally {
      setLoading(false);
    }
  }

  // events
  form.addEventListener("submit", (e) => {
    e.preventDefault();
    send(input.value);
  });

  clearBtn.addEventListener("click", () => {
    log.innerHTML = "";
  });

  newSessionBtn.addEventListener("click", () => {
    const id = genId();
    sessInp.value = id;
    localStorage.setItem("chat.session", id);
  });

  // welcome
  if (!log.children.length) {
    const tip = `Connected. Scope: ${scopeSel.value}. Type your message and press Enter.`;
    const el = document.createElement("div");
    el.className = "msg bot empty";
    el.textContent = tip;
    log.appendChild(el);
  }
})();
