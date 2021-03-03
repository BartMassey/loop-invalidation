fn main() {
    let mut v = Vec::new();
    v.resize(5, 0);
    for i in &v {
        let _ = v.pop();
        println!("{}", i);
    }
}
