const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = process.env.GCP_PROJECT || "demo-no-project";
const ALICE_UID = "alice_student_101";
const BOB_UID = "bob_student_202";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

function pastDate(msAgo = 2000) {
  return new Date(Date.now() - msAgo);
}

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

// 1. User Profile Security & Parity
test("UserProfile: unauthenticated denied; owner CRUD works; cross-user & ghost fields denied", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(5000);

  const validProfile = {
    userId: ALICE_UID,
    displayName: "Aarav Sharma",
    streakDays: 3,
    longestStreak: 5,
    xp: 450,
    lastStudyDate: "2026-11-20",
    unlockedBadges: ["first_spark"],
    dailyGoalTopics: 4,
    reminderHour: 19,
    reminderMinute: 30,
    remindersEnabled: true,
    syllabusSeeded: true,
    createdAt: created,
    updatedAt: created,
  };

  await assertFails(unauthDb.collection("users").doc(ALICE_UID).set(validProfile));
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).set(validProfile));
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).get());

  // Partial update preserves createdAt and userId
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).update({
      streakDays: 4,
      xp: 520,
      updatedAt: pastDate(1000),
    })
  );

  // Reject cross-user read & write
  await assertFails(bobDb.collection("users").doc(ALICE_UID).get());
  await assertFails(bobDb.collection("users").doc(ALICE_UID).update({ xp: 99999, updatedAt: pastDate(500) }));

  // Reject ghost field / shadow update
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      isAdmin: true,
      updatedAt: pastDate(500),
    })
  );

  // Reject future timestamp
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      xp: 600,
      updatedAt: new Date(Date.now() + 60000),
    })
  );

  // Reject immutable createdAt mutation
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).update({
      createdAt: pastDate(100),
      updatedAt: pastDate(100),
    })
  );
});

// 2. Leaderboard Query Enforcer & Isolation
test("Leaderboard: filtered publicVisible query succeeds; unfiltered list fails; cross-user write fails", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(5000);

  const entry = {
    userId: ALICE_UID,
    displayName: "Aarav Sharma",
    xp: 650,
    streakDays: 6,
    topicsCompleted: 18,
    testsCompleted: 4,
    badgesCount: 3,
    publicVisible: true,
    createdAt: created,
    updatedAt: created,
  };

  await assertFails(unauthDb.collection("leaderboard").where("publicVisible", "==", true).get());
  await assertSucceeds(aliceDb.collection("leaderboard").doc(ALICE_UID).set(entry));

  // Cross-user write rejected
  await assertFails(bobDb.collection("leaderboard").doc(ALICE_UID).update({ xp: 0, updatedAt: pastDate(500) }));

  // Filtered query matches rule
  await assertSucceeds(bobDb.collection("leaderboard").where("publicVisible", "==", true).get());

  // Unfiltered query rejected by Query Trust rule
  await assertFails(bobDb.collection("leaderboard").get());
});

// 3. Subjects (Syllabus) CRUD & Isolation
test("Subjects: owner create/list/update succeeds; cross-user & extra id field rejected", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(5000);

  const subject = {
    courseCode: "100102",
    title: "Engineering Mathematics - I",
    credits: 3,
    category: "THEORY",
    sortOrder: 1,
    units: [
      {
        unitId: "u1",
        unitNumber: "Unit 1.0",
        title: "Basic Linear Algebra",
        hours: 8,
        topics: [
          { topicId: "t1_1", code: "1.1", title: "Elementary transformations and Rank of Matrix", completed: false },
        ],
      },
    ],
    createdAt: created,
    updatedAt: created,
  };

  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).collection("subjects").doc("sub_100102").set(subject));
  await assertSucceeds(aliceDb.collection("users").doc(ALICE_UID).collection("subjects").get());
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("subjects").doc("sub_100102").update({
      units: [
        {
          unitId: "u1",
          unitNumber: "Unit 1.0",
          title: "Basic Linear Algebra",
          hours: 8,
          topics: [
            { topicId: "t1_1", code: "1.1", title: "Elementary transformations and Rank of Matrix", completed: true },
          ],
        },
      ],
      updatedAt: pastDate(1000),
    })
  );

  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("subjects").get());
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("subjects").doc("sub_bad").set({
      ...subject,
      id: "sub_bad",
    })
  );
});

// 4. To-Do, Timetable, Questions, Reports Parity
test("Todos, Timetable, Questions, and Reports: valid Kotlin payloads pass; cross-user & invalid schemas fail", async () => {
  const aliceDb = testEnv.authenticatedContext(ALICE_UID).firestore();
  const bobDb = testEnv.authenticatedContext(BOB_UID).firestore();
  const created = pastDate(5000);

  // Todo
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("todos").doc("todo_1").set({
      title: "1.1 Elementary transformations and Rank of Matrix",
      notes: "From Engineering Mathematics - I",
      sourceType: "SYLLABUS",
      subjectId: "sub_100102",
      subjectTitle: "Engineering Mathematics - I",
      unitId: "u1",
      unitTitle: "Unit 1.0: Basic Linear Algebra",
      topicId: "t1_1",
      priority: "HIGH",
      dueLabel: "Today",
      completed: false,
      createdAt: created,
      updatedAt: created,
    })
  );
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("todos").doc("todo_1").update({
      completed: true,
      updatedAt: pastDate(1000),
    })
  );
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("todos").get());

  // Timetable
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("timetable").doc("slot_1").set({
      dayOfWeek: 1,
      startTime: "09:00",
      endTime: "10:00",
      subjectId: "sub_100102",
      subjectTitle: "Engineering Mathematics - I",
      unitHint: "Unit 1.0: Linear Algebra",
      room: "LH-104",
      slotType: "LECTURE",
      createdAt: created,
      updatedAt: created,
    })
  );

  // Question with math symbols & marking scheme
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("questions").doc("q_1").set({
      subjectId: "sub_100102",
      subjectTitle: "Engineering Mathematics - I",
      unitId: "u4",
      unitTitle: "Unit 4.0: Integral Calculus",
      topicCode: "4.4",
      topicTitle: "Beta & Gamma functions",
      questionType: "SUBJECTIVE",
      prompt: "Prove the relation between Beta and Gamma functions: B(α, β) = Γ(α)Γ(β) / Γ(α + β).",
      formulaExpr: "B(α, β) = ∫₀¹ x^(α-1)(1-x)^(β-1) dx",
      diagramKind: "INTEGRAL_AREA",
      imageUri: "",
      options: [],
      correctOptionIndex: -1,
      maxMarks: 6,
      modelAnswer: "Start from Γ(α)Γ(β) = 4 ∫₀^∞ ∫₀^∞ x^(2α-1) y^(2β-1) e^(-(x²+y²)) dx dy and switch to polar coordinates.",
      markingScheme: [
        "1M: Write integral definitions of Γ(α) and Γ(β)",
        "2M: Transform to polar coordinates x = r cos θ, y = r sin θ with Jacobian r",
        "2M: Separate radial and angular integrals",
        "1M: Conclude B(α, β) = Γ(α)Γ(β) / Γ(α + β)",
      ],
      createdAt: created,
      updatedAt: created,
    })
  );

  // Report Card (immutable archive)
  await assertSucceeds(
    aliceDb.collection("users").doc(ALICE_UID).collection("reports").doc("rep_1").set({
      subjectId: "sub_100102",
      subjectTitle: "Engineering Mathematics - I",
      unitId: "u4",
      unitTitle: "Unit 4.0: Integral Calculus",
      obtainedMarks: 8.5,
      totalMarks: 10,
      percentage: 85.0,
      durationSeconds: 420,
      totalQuestions: 3,
      correctCount: 2,
      strongTopics: ["4.1 Leibnitz rule"],
      weakTopics: ["4.3 Triple Integrals"],
      improveTopics: ["4.4 Beta & Gamma functions"],
      questionBreakdown: [
        {
          questionPrompt: "Evaluate ∫₀¹ x^(α-1)(1-x)^(β-1) dx",
          topicTitle: "Beta & Gamma functions",
          questionType: "SUBJECTIVE",
          awardedMarks: 5,
          maxMarks: 6,
          feedback: "Correct polar substitution; missed Jacobian justification.",
          userAnswer: "Using x = r cos θ...",
        },
      ],
      createdAt: created,
      updatedAt: created,
    })
  );

  // Reports cannot be tampered with via update
  await assertFails(
    aliceDb.collection("users").doc(ALICE_UID).collection("reports").doc("rep_1").update({
      obtainedMarks: 10,
      updatedAt: pastDate(500),
    })
  );
  await assertFails(bobDb.collection("users").doc(ALICE_UID).collection("reports").get());
});
