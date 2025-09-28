# Divide-and-Conquer Algorithms — Report

## Summary
This repository implements four classic divide-and-conquer algorithms with safe recursion patterns, instrumentation for metrics, and benchmarks. Implemented algorithms:

- **MergeSort** (stable, buffer reuse, insertion cutoff)
- **QuickSort** (randomized pivot, smaller-first recursion, insertion cutoff)
- **Deterministic Select** (Median-of-Medians, groups of 5)
- **Closest Pair (2D)** (O(n log n) divide-and-conquer)

This README contains concise recurrence analyses (Master theorem and Akra–Bazzi intuition), architecture notes about controlling depth/allocations, the CSV schema for metrics, and scripts to generate initial plots.

---

# Architecture notes

**Instrumentation**
- `Metrics` collects: `comparisons`, `swaps`, `allocations` (explicit temporary buffers), and recursion `maxDepth`.
- `DepthTracker` is an AutoCloseable guard used with `try (new DepthTracker(m)) { ... }` so recursion depth is decremented even on exceptions.
- All algorithms accept a `Metrics` object (nullable) and call increment helpers where comparisons/swaps/allocations happen.

**Memory and allocations**
- MergeSort uses a single reusable auxiliary buffer allocated once per top-level call (not per recursion) to minimize allocations.
- Closest Pair uses index/array-based passing of buffers to avoid frequent list allocations; strip arrays are created per recursion but bounded by input size.
- Deterministic Select performs small-group sorts in-place and moves medians to the front to avoid extra arrays.

**Safe recursion patterns**
- QuickSort: randomized pivot + always recurse on the smaller partition, iterate on the larger one (tail-call elimination pattern). This bounds recursion depth to O(log n) on average.
- MergeSort & Closest Pair: balanced splits produce recursion depth Θ(log n). DepthTracker records empirical maxima.
- Deterministic Select: recurses on a strictly smaller fraction of the input (median-of-medians guarantees constant fraction shrink), yielding O(log n) depth in practice.

---

# Recurrence analyses (copy-ready paragraphs)

## MergeSort
Recurrence: $$T(n)=2T\left(\frac{n}{2}\right)+\Theta(n).$$
By the Master Theorem, with parameters \(a=2, b=2, f(n)=\Theta(n)\) and \(n^{\log_b a}=n\), we are in case 2; thus \(T(n)=\Theta(n\log n)\). The recursion depth is \(\Theta(\log n)\); merging contributes linear work per level. In practice, using a reusable buffer and an insertion-sort cutoff reduces constants for small inputs.

## QuickSort (randomized)
Expected recurrence (randomized pivots): \(T(n)=T(\alpha n)+T((1-\alpha)n)+\Theta(n)\) where \(\alpha\) is a random split fraction. Probabilistic analysis gives expected \(T(n)=\Theta(n\log n)\). Randomized pivoting avoids adversarial worst cases, and recursing on the smaller partition bounds the expected stack depth to \(O(\log n)\). Worst-case time without randomization remains \(\Theta(n^2)\), but is extremely unlikely with random pivots.

## Deterministic Select (Median-of-Medians)
Using groups of 5, the recurrence (intuition) becomes \(T(n)=T(\lceil n/5\rceil)+T(7n/10)+\Theta(n)\). Akra–Bazzi intuition (or linear-recursion analysis) shows that the recursive sizes are bounded by constant fractions less than 1, and the linear partitioning work dominates, giving \(T(n)=\Theta(n)\) worst-case. The constant factors are larger than randomized select due to extra grouping/median computations.

## Closest Pair (2D)
Recurrence: \(T(n)=2T(n/2)+\Theta(n)\) when y-order merging is done by linear-time merging. By the Master Theorem this is \(\Theta(n\log n)\). The strip-check step is linear per recursion level and each point needs only a bounded number (≤7-8) of neighbor distance checks.

---

# Measurements, CSV format & example

**Collected per-run fields**
- `algo` — algorithm name (merge, quick, select, closest)
- `n` — input size
- `distribution` — e.g., `random`, `sorted`, `reverse`, `duplicates`, `uniformPoints`
- `run_id` — numeric run index
- `time_ns` — elapsed wall-clock time in nanoseconds (System.nanoTime)
- `comparisons` — instrumented comparison counter
- `swaps` — instrumented swap counter
- `allocations` — explicit temporary allocations counted in code (buffers, strips)
- `maxDepth` — maximal recursion depth observed

**CSV header (one-row-per-run)**
```
algo,n,distribution,run_id,time_ns,comparisons,swaps,allocations,maxDepth
```

**Example row**
```
merge,10000,random,1,123456789,23456,10000,1,15
```

---

# Minimal commands

Build:
```
mvn clean package
```

Run single CLI run (example):
```
java -jar target/app-1.0-SNAPSHOT-jar-with-dependencies.jar mergesort 10000 mergesort.csv
```

Run many sizes (example bash loop):
```bash
for n in 256 512 1024 2048 4096 8192 16384; do
  java -jar target/app-1.0-SNAPSHOT-jar-with-dependencies.jar mergesort $n mergesort.csv
done
```

---

