/**
 * StudyApp Showcase Website — Direct APK Download & Interactive Phone Preview
 *
 * TO ENABLE 1-TAP DIRECT APK DOWNLOAD ON YOUR HOSTED SITE:
 * 1. Download your built APK from AI Studio (Preview -> Install, or Settings -> Download APK).
 * 2. Place it inside this `docs/` folder as `StudyApp-v1.0.apk` (or set `apkUrl` below to a
 *    direct GitHub Release asset URL like:
 *    "https://github.com/<user>/<repo>/releases/latest/download/StudyApp-v1.0.apk").
 */
const APK_CONFIG = {
  appName: "StudyApp",
  version: "1.0.0",
  apkFileName: "StudyApp-v1.0.apk",
  apkUrl: "https://github.com/AnkushXTanjiro/StudyApp/releases/latest/download/StudyApp-v1.0.apk",
  minAndroid: "Android 7.0+",
  telegramUrl: "https://t.me/Lord_Yoriche",
};

// 1. Interactive Phone Preview Tabs
const navButtons = document.querySelectorAll(".phone-bottom-nav .p-nav-btn");
const screenPanes = document.querySelectorAll("#phoneScreen .screen-pane");
const phoneOverlay = document.getElementById("phoneOverlay");
const phoneOverlayContent = document.getElementById("phoneOverlayContent");

function switchPhoneTab(tabKey) {
  closePhoneOverlay();
  navButtons.forEach((btn) => {
    btn.classList.toggle("active", btn.dataset.tab === tabKey);
  });
  screenPanes.forEach((pane) => {
    pane.classList.toggle("active", pane.dataset.pane === tabKey);
  });
}

navButtons.forEach((btn) => {
  btn.addEventListener("click", () => switchPhoneTab(btn.dataset.tab));
});

document.querySelectorAll("[data-jump-tab]").forEach((el) => {
  el.addEventListener("click", () => switchPhoneTab(el.dataset.jumpTab));
});

// 2. Interactive In-Phone Overlays (Makina AI & Developer Profile)
function openPhoneOverlay(type) {
  if (!phoneOverlay || !phoneOverlayContent) return;

  if (type === "makina") {
    phoneOverlayContent.innerHTML = `
      <div style="display:flex;justify-content:space-between;align-items:center;">
        <strong style="color:#FFC95C;">✨ Makina • AI Study Companion</strong>
        <button type="button" class="mini-icon-btn" id="closePhoneOverlayBtn">✕</button>
      </div>
      <div style="color:#94A3B8;font-size:0.68rem;">Gemini Voice Ready • Offline TTS Fallback</div>
      <div style="background:rgba(255,201,92,0.12);border:1px solid rgba(255,201,92,0.35);border-radius:11px;padding:9px;color:#F8FAFC;">
        "Hey Ankush, back already? I like that devotion. Finish a test and tap <strong>Explain with AI</strong> — or hold the mic and ask me anything you didn't get."
      </div>
      <div style="display:flex;gap:6px;">
        <span style="flex:1;background:#060D1F;border:1px solid rgba(56,189,248,0.3);border-radius:9px;padding:7px 9px;color:#94A3B8;">Tap 🎙 Mic or ask Makina...</span>
        <span style="background:rgba(56,189,248,0.22);border:1px solid #38BDF8;border-radius:9px;padding:7px 9px;color:#7DD3FC;">🎙</span>
      </div>
    `;
  } else {
    phoneOverlayContent.innerHTML = `
      <div style="display:flex;justify-content:space-between;align-items:center;">
        <strong style="color:#7DD3FC;">&lt;/&gt; Developer &amp; Creator</strong>
        <button type="button" class="mini-icon-btn" id="closePhoneOverlayBtn">✕</button>
      </div>
      <div style="font-weight:800;font-size:0.9rem;">Ankush Kumar</div>
      <div style="color:#7DD3FC;font-size:0.7rem;">B.Tech CSE • GEC Sheikhpura (2026–2030)</div>
      <div style="color:#CBD5E1;">Building helpful tools, exam banks &amp; AI learning experiences for BEU students.</div>
      <div style="color:#34D399;font-weight:700;">Telegram: @Lord_Yoriche</div>
    `;
  }

  phoneOverlay.classList.add("open");
  phoneOverlay.setAttribute("aria-hidden", "false");

  const closeBtn = document.getElementById("closePhoneOverlayBtn");
  if (closeBtn) closeBtn.addEventListener("click", closePhoneOverlay);
}

function closePhoneOverlay() {
  if (!phoneOverlay) return;
  phoneOverlay.classList.remove("open");
  phoneOverlay.setAttribute("aria-hidden", "true");
}

document.querySelectorAll("[data-phone-modal]").forEach((btn) => {
  btn.addEventListener("click", () => openPhoneOverlay(btn.dataset.phoneModal));
});

if (phoneOverlay) {
  phoneOverlay.addEventListener("click", (e) => {
    if (e.target === phoneOverlay) closePhoneOverlay();
  });
}

// 3. Direct APK Download Handler (checks availability before falling back to helpful instructions)
const apkModal = document.getElementById("apkModal");
const apkModalTitle = document.getElementById("apkModalTitle");
const apkModalBody = document.getElementById("apkModalBody");
const closeApkModalBtn = document.getElementById("closeApkModal");

function openApkModal(title, html) {
  if (!apkModal || !apkModalTitle || !apkModalBody) return;
  apkModalTitle.textContent = title;
  apkModalBody.innerHTML = html;
  apkModal.classList.add("open");
  apkModal.setAttribute("aria-hidden", "false");
}

function closeApkModal() {
  if (!apkModal) return;
  apkModal.classList.remove("open");
  apkModal.setAttribute("aria-hidden", "true");
}

if (closeApkModalBtn) closeApkModalBtn.addEventListener("click", closeApkModal);
if (apkModal) {
  apkModal.addEventListener("click", (e) => {
    if (e.target === apkModal) closeApkModal();
  });
}

function triggerFileDownload(url, fileName) {
  const link = document.createElement("a");
  link.href = url;
  link.download = fileName;
  link.rel = "noopener";
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

async function handleApkDownload() {
  const url = APK_CONFIG.apkUrl;
  const isExternal = /^https?:\/\//i.test(url);

  if (isExternal) {
    triggerFileDownload(url, APK_CONFIG.apkFileName);
    openApkModal(
      "Download Started",
      `
        <p>Your <strong>${APK_CONFIG.appName} v${APK_CONFIG.version}</strong> APK download has started.</p>
        <p>On your Android phone, open <strong>Downloads</strong>, tap <code>${APK_CONFIG.apkFileName}</code>, allow <strong>Install unknown apps</strong> if prompted, and tap <strong>Install</strong>.</p>
      `
    );
    return;
  }

  try {
    const res = await fetch(url, { method: "HEAD" });
    if (res.ok) {
      triggerFileDownload(url, APK_CONFIG.apkFileName);
      openApkModal(
        "Download Started",
        `
          <p>Downloading <code>${APK_CONFIG.apkFileName}</code> to your phone now.</p>
          <p>Once finished, tap the file in your notification bar or <strong>Files → Downloads</strong> and press <strong>Install</strong>.</p>
        `
      );
      return;
    }
  } catch (_) {
    // Fallback when opened locally via file:// or when APK file has not been placed yet
  }

  openApkModal(
    "Attach Your APK File to Enable 1-Tap Download",
    `
      <p>This website is ready for direct phone downloads! To serve the live APK from this button:</p>
      <ol style="padding-left:18px;margin:10px 0;display:flex;flex-direction:column;gap:8px;">
        <li>Download the built APK from Google AI Studio (click <strong>Install</strong> above the phone preview) or Android Studio.</li>
        <li>Place the file in the <code>docs/</code> folder named <code>${APK_CONFIG.apkFileName}</code> (or update <code>APK_CONFIG.apkUrl</code> in <code>docs/app.js</code> to your GitHub Releases APK link).</li>
        <li>Enable <strong>GitHub Pages → Deploy from /docs</strong> (or host on Netlify / Vercel).</li>
      </ol>
      <p style="margin-top:12px;">Once uploaded, tapping <strong>Download APK</strong> on any Android phone will immediately download and install StudyApp.</p>
    `
  );
}

document.querySelectorAll(".js-download-apk").forEach((btn) => {
  btn.addEventListener("click", handleApkDownload);
});

// 4. Copy Telegram Handle Button
const copyTelegramBtn = document.getElementById("copyTelegramBtn");
if (copyTelegramBtn) {
  copyTelegramBtn.addEventListener("click", async () => {
    try {
      await navigator.clipboard.writeText("@Lord_Yoriche");
      copyTelegramBtn.textContent = "✓ Copied @Lord_Yoriche";
      setTimeout(() => {
        copyTelegramBtn.textContent = "Copy @Lord_Yoriche";
      }, 2200);
    } catch (_) {
      copyTelegramBtn.textContent = "@Lord_Yoriche";
    }
  });
}
