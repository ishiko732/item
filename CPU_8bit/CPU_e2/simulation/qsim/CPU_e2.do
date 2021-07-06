onerror {quit -f}
vlib work
vlog -work work CPU_e2.vo
vlog -work work CPU_e2.vt
vsim -novopt -c -t 1ps -L cycloneii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.CPU_e2_vlg_vec_tst
vcd file -direction CPU_e2.msim.vcd
vcd add -internal CPU_e2_vlg_vec_tst/*
vcd add -internal CPU_e2_vlg_vec_tst/i1/*
add wave /*
run -all
