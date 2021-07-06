onerror {quit -f}
vlib work
vlog -work work ROM_IR.vo
vlog -work work ROM_IR.vt
vsim -novopt -c -t 1ps -L cycloneii_ver -L altera_ver -L altera_mf_ver -L 220model_ver -L sgate work.ROM_IR_8bit_vlg_vec_tst
vcd file -direction ROM_IR.msim.vcd
vcd add -internal ROM_IR_8bit_vlg_vec_tst/*
vcd add -internal ROM_IR_8bit_vlg_vec_tst/i1/*
add wave /*
run -all
